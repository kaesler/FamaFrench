package kae.ff3

/**
 * Class to represent a particular calendar month in a particular year.
 */
class Month(val year: Int, val mm: Int)
  extends Ordered[Month]
{
  require(year > 1930)
  require(year < 2011)
  require(mm >= 0)
  require(mm <= 11)

  /**
   * Construct from string of the form "YYYYMM"
   */
  def this(str: String) = {
   //require(str.length == 6)
   this(str.substring(0, 4).toInt, str.substring(4, 2).toInt)
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
}