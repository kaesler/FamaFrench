package kae.ff3

import java.io.FileWriter
import java.io.File
import java.util.Calendar
import scala.collection.immutable.TreeMap

class MutualFundMonthlyReturnHistory(
  val ticker: String,
  isInternational: Boolean,
  yahooData: Seq[YahooPriceDatum])
{ 
  // Record number of days and months in original data for sanity checking.
  val yahooDayCount = yahooData.size
  val yahooMonthCount = ((yahooData map { datum => datum.month } toSeq).distinct).size
  val ffDigest = (if (isInternational) FamaFrenchDigestInternational else FamaFrenchDigestUsa)
  
  // Store intermediate computations for sanity checking
  private var adjustedCloses = TreeMap[Month, Double]()
  
  private def computeTotalReturns : TreeMap[Month, Double] = {
	validateYahooData
	
	var result = TreeMap[Month, Double]()
	val monthlyData = (
	  // Group by month (Map[Month, Seq[YahooPriceDatum]	 
	  (yahooData.groupBy { datum => datum.month } values)
	  // Keep the latest datum in each month	
	  map { datums =>	 
	    datums reduceLeft { (d1, d2) =>	    
	      if (d1.calendar.getTimeInMillis > d2.calendar.getTimeInMillis) d1 else d2 }} toSeq)

	require(monthlyData.size == yahooMonthCount)
	      
	// Sort by ascending month
	val sortedMonthlyData = monthlyData sortWith { (d1, d2) => d1.month < d2.month }
	
	require(sortedMonthlyData.size == yahooMonthCount)
	
	// Store in adjustedCloses for sanity checking.
	sortedMonthlyData foreach { datum =>
	  adjustedCloses += (datum.month -> datum.adjustedClose)
	}
	
	require(adjustedCloses.size == yahooMonthCount)

	// Compute return (as a percentage) for 2nd and subsequent months        
    sortedMonthlyData.sliding(2) foreach { s =>
	  val firstDatum = s.head
	  val secondDatum = s.last
	  result += (secondDatum.month
	             ->
                 (100.0
	              *
	              ((secondDatum.adjustedClose - firstDatum.adjustedClose))/firstDatum.adjustedClose))
      }

	require(result.size == (yahooMonthCount - 1))

	result 
  }
  private val totalReturns = computeTotalReturns;      
	
  private def computeRiskAdjustedTotalReturns : TreeMap[Month, Double] = {	      
	var result = TreeMap[Month, Double]()
	val ffMonths = ffDigest.months
	
	totalReturns foreach { elt  =>
	  val (month, totalReturn) = elt
	  if (ffMonths.contains(month)) {
	    result += (month -> (totalReturn - ffDigest.getMetrics(month).riskFree))
	  }
	}
	
	result
  }
  private val riskAdjustedTotalReturns = computeRiskAdjustedTotalReturns
  
  private def validateYahooData = {
    // TODO: check no gaps etc
  }
  
  def riskAdjustedTotalReturn(month: Month) : Double = {
    riskAdjustedTotalReturns(month)
  }

  private def generateSpreadSheetContents : String = {
    val result = new StringBuilder("")
    
    result.append("%s,%s,%s,%s,%s,%s,%s,%s,%s\n".format(
    		      "Month", 
    		      "%s adjusted close".format(ticker),
    		      "Return",
    		      "Risk adjusted return",
    		      "",
    		      "Mkt-RF",
    		      "SmB",
    		      "HmL",
                  "RF"));
    var ffMonths = FamaFrenchDigestUsa.months
    riskAdjustedTotalReturns foreach { pair =>
      val (month, riskAdjustedReturn) = pair
      val ffMetrics = FamaFrenchDigestUsa.getMetrics(month)
      result.append("%s,%s,%7.4f,%7.4f,%s,%s,%s,%s,%s\n".format(
  		            month.toString,
  		            adjustedCloses(month),
   		            totalReturns(month),
   		            riskAdjustedTotalReturns(month),
    		        "",
    		        ffMetrics.marketMinusRiskFree,
    		        ffMetrics.smallMinusBig,
    		        ffMetrics.highMinusLow,
    		        ffMetrics.riskFree))
    }
    result.toString
  }
  
 def fillFile(file: File, contents: String) = {
    val writer = new FileWriter(file)
    writer.write(contents)
    writer.close
 }

  def generateRegressionSpreadsheet = {
    val contents = generateSpreadSheetContents
    //print(contents)
    fillFile(FileLocater.locateFundSpreadsheetFile(ticker), contents)
  }

  def earliestMonth : Month = {
	totalReturns.firstKey
  }

  def latestMonth : Month = {
	totalReturns.lastKey
  }
  
  def monthCount : Int = {
	totalReturns.size
  }
}

object MutualFundMonthlyReturnHistory
{
  def createFromFile(ticker: String, isInternational: Boolean) : MutualFundMonthlyReturnHistory  = {
    new MutualFundMonthlyReturnHistory(ticker, isInternational, YahooPriceDatum.parseFile(ticker))  }
}
