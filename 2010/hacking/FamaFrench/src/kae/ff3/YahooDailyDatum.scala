package kae.ff3
import java.io.File

import java.util.Date
import java.util.Calendar
import java.text.SimpleDateFormat

import scala.collection.mutable
import scala.io.Source
import scala.util.matching.Regex

class YahooPriceDatum(
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
  def create(csvLine: String) : YahooPriceDatum = {
	
	val lineRegexp(s1, s2, s3, s4, s5, s6, s7) = csvLine
	new YahooPriceDatum(new SimpleDateFormat("yyyy--mm-dd").parse(s1),
			            s2.toDouble,
			            s3.toDouble,
			            s4.toDouble,
			            s5.toDouble,
			            s6.toInt,
			            s7.toDouble) 
  }
}
