package kae.ff3

/**
 * Class to represent a particular calendar month in a particular year.
 */
class Month(val year: Int, val mm: Int)
  extends Ordered[Month]
{
  require(year > 1925)
  require(year < 2011)
  require(mm >= 1)
  require(mm <= 12)

  /**
   * Construct from string of the form "YYYYMM"
   */
  def this(str: String) = {
   //require(str.length == 6)
   this(str.substring(0, 4).toInt, str.substring(4, 6).toInt)
  }

  override def equals(other: Any) : Boolean = other match {
  case that: Month =>
    this.year.equals(that.year) && this.mm.equals(that.mm)

    case _ =>
      false
  }
  
  override def hashCode : Int = 
  41 * (
	41 + year
  ) + mm

  def compare(that: Month) : Int = {
    if (this.year.equals(that.year)) {
      this.mm.compare(that.mm)
    } else {
      this.year.compare(that.year)
    }
  }
  
  /**
   * Compute the next month.
   */
  def successor : Month = {
    if (mm == 12) {
      new Month(year + 1, 1)
    } else {
      new Month(year, mm + 1)
    }
  }
  
  private val monthNames = Array("invalid", "jan", "feb", "mar", "apr",
		                         "may", "jun", "jul", "aug",
		                         "sep", "oct", "nov", "dec")
  
  override def toString = "%d-%s".format(year, monthNames(mm))
}