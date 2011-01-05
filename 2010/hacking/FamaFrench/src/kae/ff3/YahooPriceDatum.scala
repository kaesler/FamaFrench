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

  val month = new Month(calendar)
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
  
  /**
   * Parse data file into sequence of daily price data.
   * @param ticker
   * @return
   */
  def parseFile(ticker: String) : Seq[YahooPriceDatum] = {
    val file = FileLocater.locateFundDataFile(ticker)
    require(file.exists)
    
   	(Source.fromFile(file).getLines()
     // Drop the leading few lines that are not monthly data lines
     dropWhile { line => !matches(line) }

     // Retain the monthly data lines
     takeWhile { line => matches(line) }

     // Foreach monthly data line create a datum
     map { dataLine => create(dataLine) }).toSeq
  }
}
