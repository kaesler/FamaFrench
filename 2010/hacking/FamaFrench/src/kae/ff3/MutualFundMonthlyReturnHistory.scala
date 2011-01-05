package kae.ff3
import scala.io.Source
import java.util.Date;import java.util.Calendar;import java.text.SimpleDateFormat
import scala.collection.immutable.TreeMap

/**
 * Class to encapsulate the monthly total return history for a mutual fund or ETF. * We construct it from raw Yahoo daily data. * Internal code processes that into more suitable form.
 */
class MutualFundMonthlyReturnHistory(
  val ticker: String,
  yahooData: Seq[YahooPriceDatum]
)
{  private def computeMonthlyTotalReturns : TreeMap[Month, Double] = {	// todo: why are semi-colons necessary here?????	var result = TreeMap[Month, Double]();	// TODO	//  - sort the Yahoo data in ascending data	//  - validate in any way we can	//  - need to discard the first month	//  - compute monthly returns from daily data	 	val a = (yahooData      // Group by month (Map[Month, Seq[YahooPriceDatum]	  groupBy { datum => datum.month });		val b = a.values;	// Keep the latest datum in each month	val c = b.map { datums =>	  datums reduceLeft { (d1, d2) =>	    if (d1.calendar.getTimeInMillis > d2.calendar.getTimeInMillis) d1 else d2 }};	// Sort by ascending month    val d = c.toSeq sortWith { (d1, d2) => d1.month < d2.month };        // @ todo compute return (as a percentage) for 2nd and subsequent months        d foreach { datum =>       result += (datum.month -> datum.adjustedClose); };	result  };  private def computeMonthlyRiskFreeRelativeTotalReturns : TreeMap[Month, Double] = {	      // todo: remove	new TreeMap[Month, Double]()  };  private val monthlyTotalReturns = computeMonthlyTotalReturns;      private def validateYahooData = {
    // todo: check no gaps etc
  }
  
  def earliestMonth : Month = {
	monthlyTotalReturns.firstKey
  }

  def latestMonth : Month = {
	monthlyTotalReturns.lastKey
  }
  
  def monthCount : Int = {
	monthlyTotalReturns.size
  }
}

object MutualFundMonthlyReturnHistory
{
  def createFromFile(ticker: String) : MutualFundMonthlyReturnHistory  = {
    new MutualFundMonthlyReturnHistory(ticker, YahooPriceDatum.parseFile(ticker))  }
}
