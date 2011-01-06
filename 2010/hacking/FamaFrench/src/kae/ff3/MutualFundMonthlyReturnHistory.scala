package kae.ff3
import java.util.Calendar
import scala.collection.immutable.TreeMap

class MutualFundMonthlyReturnHistory(
  val ticker: String,
  yahooData: Seq[YahooPriceDatum])
{  
  private def computeMonthlyTotalReturns : TreeMap[Month, Double] = {
	validateYahooData
	
	var result = TreeMap[Month, Double]()
	val monthlyData = (
	  // Group by month (Map[Month, Seq[YahooPriceDatum]	 
	  (yahooData.groupBy { datum => datum.month } values)
	    
	  // Keep the latest datum in each month	
	  map { datums =>	 
	    datums reduceLeft { (d1, d2) =>	    
	      if (d1.calendar.getTimeInMillis > d2.calendar.getTimeInMillis) d1 else d2 }} toSeq)
	       
	// Sort by ascending month    
	val sortedMonthlyData = monthlyData sortWith { (d1, d2) => d1.month < d2.month }
	   
	// Compute return (as a percentage) for 2nd and subsequent months        
    val x = sortedMonthlyData.sliding(2) foreach { s =>
	   val firstDatum = s.head
	   val secondDatum = s.last
	   result += (secondDatum.month
	  		      ->
	              (100.0
	               *
	               ((secondDatum.adjustedClose - firstDatum.adjustedClose))/firstDatum.adjustedClose))
    }
	    
	result 
  }
  private val monthlyTotalReturns = computeMonthlyTotalReturns;      
	
  private def computeMonthlyRiskFreeRelativeTotalReturns : TreeMap[Month, Double] = {	      
	var result = TreeMap[Month, Double]()
	monthlyTotalReturns foreach { elt  =>
	  val (month, totalReturn) = elt
	  result += (month -> (totalReturn - FamaFrenchDigestUsaMonthly.getMetrics(month).riskFree))
	}
	
	result
  }
  private val riskFreeRelativeTotalReturns = computeMonthlyRiskFreeRelativeTotalReturns
  
  private def validateYahooData = {
    // TODO: check no gaps etc
  }
  
  def riskFreeRelativeTotalReturn(month: Month) : Double = {
    riskFreeRelativeTotalReturns(month)
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
    new MutualFundMonthlyReturnHistory(ticker, YahooPriceDatum.parseFile(ticker))  }
}
