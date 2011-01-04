package kae.ff3
import scala.io.Source
import java.util.Date;import java.util.Calendar;import java.text.SimpleDateFormat
import scala.collection.immutable.TreeMap

/**
 * Class to encapsulate the monthly total return history for a mutual fund or ETF.
 */
class MutualFundMonthlyReturnHistory(
  val ticker: String,
  val returns: TreeMap[Month, Double]
)
{
  def validate = {
    // todo: check no gaps etc
  }
  
  def earliestMonth : Month = {
	returns.firstKey
  }

  def latestMonth : Month = {
	returns.lastKey
  }
  
  def monthCount : Int = {
	returns.size
  }
}

object MutualFundMonthlyReturnHistory
{
  def create(
    ticker: String,
    yahooData: Seq[YahooPriceDatum]
  ) : MutualFundMonthlyReturnHistory = {
    val map = new TreeMap[Month, Double]()

    // todo
	//  - sort the data in ascending data
	//  - validate in any way we can
	//  - need to discard the first month
	//  - compute monthly returns from daily data
	  

    new MutualFundMonthlyReturnHistory(ticker, map)
  }
  
  def createFromFile(ticker: String) = {
	  
    val file = FileLocater.locateFundDataFile(ticker)
    require(file.exists)
    
    // E.g:
    //  "Date,Open,High,Low,Close,Volume,Adj Close"
    //  "2010-12-31,16.63,16.63,16.63,16.63,000,16.63"

    // Seven non-empty string values separated by commas.
	val lineRegexp = """([0-9][0-9][0-9][0-9]-[0-9]+-[0-9]+),([^,]+),([^,]+),([^,]+),([^,]+),([^,]+),([^,]+)""".r;		def isDataLine(line: String) : Boolean = {      lineRegexp findFirstIn line match {        case Some(s) => true    	case None => false       }    }

   	val rawSeq = (Source.fromFile(file).getLines()
     // Drop the leading lines that are not monthly data lines
     dropWhile { line => ! isDataLine(line) }

     // Retain the daily data lines
     takeWhile { line => isDataLine(line) }
     // For each daily data line
     map { dailyDataLine =>
       val lineRegexp(s1, s2, s3, s4, s5, s6, s7) = dailyDataLine       // Pull out: date and adjusted close
       (new SimpleDateFormat("yyyy-mm-dd").parse(s1), s7.toDouble)
    
     }).toSeq

    
	  
  }
}
