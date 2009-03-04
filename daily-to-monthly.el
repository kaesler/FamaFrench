(defvar header-regexp "Date,Open,High,Low,Close,Volume,Adj Close")
(defvar detail-regexp "\\([0-9][0-9][0-9][0-9]\\)-\\([0-9][0-9]\\)-\\([0-9][0-9]\\),")

;; Assumes lines begin with a date in the format: yyyy-mm-dd and are already sorted ascending.
;;
(defun daily-to-monthly-region (begin end)
  (interactive "r")
  (goto-char begin)

  (save-excursion
    (goto-char begin)

    ;; Make a marker, in case the buffer changes size because
    ;; of the effects of func.
    (let ((end-marker (copy-marker end)))
      (while (< (point) (marker-position end-marker))
        (beginning-of-line)

        (collapse-current-month)
        (forward-line 1)))))

(defun daily-to-monthly ()
  (interactive)

  ;;(reformat-dates)

  ;; Sort the file into axcending chronological order
  ;;
  (sort-detail-lines-ascending)

  ;; Go to the first detail line
  ;;
  (goto-char (point-min))
  (forward-line 1)

  ;; Collapse all months by keeping only the latest day in the month
  ;;
  (while (not (eobp))
    (collapse-current-month)
    (forward-line 1)))

(defun sort-detail-lines-ascending ()
  (goto-char (point-min))
  (if (not (looking-at header-regexp))
      (error "Expected to see header line"))
  (forward-line 1)
  (if (not (looking-at detail-regexp))
      (error "Expected to see detail line"))

  (sort-lines nil (point) (point-max)))

(defun collapse-current-month ()
  ;; We're at the start of a new month
  ;;
  (let ((current-year (get-detail-year))
        (current-month (get-detail-month))
        (beginning-of-month (point)))

    (while (and (not (eobp))
                (current-detail-line-is-in-same-month current-year current-month))
      (forward-line 1))

    ;; If we moved at all delete the non-last days of the month.
    ;;
    (if (not (= (point) beginning-of-month))
        (progn
          ;; Move back to start of last day of month line
          ;; and delete from there back to beginning-of-month.
          ;;
          (forward-line -1)
          (delete-region beginning-of-month (point)))))

  ;; Leave point at the start of the last detail line for the month
  ;;
  )

(defun get-detail-year ()
  (if (not (looking-at detail-regexp))
      (error "Expected to see line beginning with a date")
    (match-string 1)))

(defun get-detail-month ()
  (if (not (looking-at detail-regexp))
      (error "Expected to see line beginning with a date")
    (match-string 2)))

(defun current-detail-line-is-in-same-month (year month)
  (and (equal month (get-detail-month))
       (equal year (get-detail-year))))

(defun reformat-dates ()
  (interactive)
  ;; Go to the first detail line
  ;;
  (goto-char (point-min))
  (forward-line 1)

  (while (not (eobp))
    (reformat-current-date)
    (forward-line 1)))

(defun reformat-current-date ()
  (if (not (looking-at "\\([0-9]+\\)/\\([0-9]+\\)/\\([0-9][0-9][0-9][0-9]\\)"))
      (error "Expected detail line (raw date format)"))
  (let* ((month (parse-integer (match-string 1)))
         (day (parse-integer (match-string 2)))
         (year (parse-integer (match-string 3)))
         (reformatted-date (format "%4d-%02d-%02d" year month day)))
    (delete-region (match-beginning 0) (match-end 0))
    (insert reformatted-date)))

