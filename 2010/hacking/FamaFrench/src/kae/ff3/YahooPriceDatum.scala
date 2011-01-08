package kae.ff3
import java.io.File

import java.util.Date
import java.util.Calendar
import java.text.SimpleDateFormat

import scala.collection.mutable
import scala.io.Source
import scala.util.matching.Regex

/**
 * Class to represent a daily price datum from Yahoo.
 * We're really only interested in adjustedClose for now.
 */
class YahooPriceDatum(
  val date: Date,
  open: Double,
  high: Double,
  low: Double,
  close: Double,
  volume: Int,
  val adjustedClose: Double
) extends Ordered[YahooPriceDatum]
{
	  // TODO: hashCode and equals

  val calendar = Calendar.getInstance()
  calendar.setTime(date)

  val month = new Month(calendar)
  
  // Assign natural order by date.
  def compare(that: YahooPriceDatum): Int = {
    calendar.compareTo(that.calendar)
  }
  
  override def toString = calendar.toString
}

object YahooPriceDatum
{
  // Regexp for Yahoo daily price datum.
  // Seven non-empty string values separated by commas.
  // e.g.: "2008-10-14,13.79,13.79,9.16,10.84,0,10.61"
  private val lineRegexp = """([0-9][0-9][0-9][0-9]-[0-9]+-[0-9]+),(.+),(.+),(.+),(.+),(.+),(.+)""".r

  def matches(line: String) : Boolean = {
    lineRegexp findFirstIn line match {
      case Some(s) => true
      case None => false
    }
  }

  /**
   * Factory method.
   * Construct from a line in a CSV file downloaded from Yahoo.
   * e.g. 
   */
  def create(line: String) : YahooPriceDatum = {
	val lineRegexp(s1, s2, s3, s4, s5, s6, s7) = line
	val result = new YahooPriceDatum(new SimpleDateFormat("yyyy-MM-dd").parse(s1),
			            s2.toDouble,
			            s3.toDouble,
			            s4.toDouble,
			            s5.toDouble,
			            s6.toInt,
			            s7.toDouble) 
	//println("%s ==> %s".format(s1, result.calendar.get(Calendar.MONTH)))
	result
  }
  
  /**
   * Parse data file into sequence of daily price data.
   * @param ticker
   * @return
   */
  def parseFile(ticker: String) : Seq[YahooPriceDatum] = {
    val file = FileLocater.locateFundDataFile(ticker)
    require(file.exists)
    
   	(Source.fromFile(file).getLines()
     // Drop the leading few lines that are not data lines
     dropWhile { line => !matches(line) }

     // Retain the data lines
     takeWhile { line => matches(line) }

     // Foreach data line create a datum
     map { dataLine => create(dataLine) }).toSeq
  }
}
