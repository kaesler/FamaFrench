package kae.ff3
import scala.collection.immutable.TreeMap

object FamaFrenchDigestInternational extends FamaFrenchDigest
{
  // We must combine 3 data sources
  //  1. Ken French's international data
  //  3. Risk free return numbers from Ken French's USA research data
  //  4. Data I assemble for International SmB
	
  private val internationalMetrics = FamaFrenchMetricsInternational.parseFile
  private val riskFreeReturns = FamaFrenchDigestUsa.riskFreeReturns
  private val smallMinusBigValues = InternationalSmallMinusBig.parseFile

  // TODO: Must intersect the 3 month sets

  // Construct the above from the raw data below.
  private def makeMetricsMap : TreeMap[Month, FamaFrenchMetrics] = {

	// Compute the intersection of the sets of months
	val monthsCovered = internationalMetrics.keySet & riskFreeReturns.keySet & smallMinusBigValues.keySet

	var result = TreeMap[Month, FamaFrenchMetrics]()
    
	monthsCovered foreach { month =>
	   result += (month
	  		      ->
	              new FamaFrenchMetrics(
	             	// Mkt-Rf:
	                internationalMetrics(month).marketReturn - riskFreeReturns(month),
	                // SmB:
	                smallMinusBigValues(month),
	                // HmL
	                internationalMetrics(month).highMinusLow,
	                // Rf
	                riskFreeReturns(month)))
	}
    result
  }

  private val metricsMap = makeMetricsMap 

  private def validate = {
	// Make sure there are no gaps. I.e. the successor of all the keys except
	// the last one must be contained in the key set.
	val lastMonth = metricsMap.keys.last
	metricsMap.keys foreach { month =>
	  if (month != lastMonth) {
	    require(metricsMap.contains(month.successor))
	  }
	}
  }

  def getMetrics (month: Month) : FamaFrenchMetrics = metricsMap(month)

  def months  = metricsMap.keySet

}