package kae.ff3

import java.util.Date
import java.util.Calendar
import java.text.SimpleDateFormat

import scala.util.matching.Regex

class YahooDailyPriceDatum(
  date: Date,
  open: Double,
  high: Double,
  low: Double,
  close: Double,
  volume: Int,
  val adjustedClose: Double
)
{
  val calendar = Calendar.getInstance()
  calendar.setTime(date)

  /**
   * Return the month for this datum
   */
  def month : Month = {
    new Month(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH))
  }
}

object YahooDailyPriceDatum
{
  /**
   * Factory method.
   * Construct from a line in a CSV file downloaded from Yahoo.
   * e.g. "10/1/2008,13.79,13.79,9.16,10.84,0,10.61"
   */
  def create(csvLine: String) : YahooDailyPriceDatum = {
	// Seven non-empty string values separated by commas.
	val lineRegexp = """(.+),(.+),(.+),(.+),(.+),(.+),(.+)""".r
	val lineRegexp(s1, s2, s3, s4, s5, s6, s7) = csvLine
	new YahooDailyPriceDatum(new SimpleDateFormat("mm/dd/yyyy").parse(s1),
			                 s2.toDouble,
			                 s3.toDouble,
			                 s4.toDouble,
			                 s5.toDouble,
			                 0,
			                 s7.toDouble) 
  }
}
