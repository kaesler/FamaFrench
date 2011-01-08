package kae.ff3
import scala.collection.immutable.TreeMap

object FamaFrenchDigestInternational extends FamaFrenchDigest
{
  // TODO: we must combine 3 data sources
  //  1. Ken French's international data
  //  3. Ken French's USA data (for risk free data)
  //  4. 
  private val rawInternationalData = FamaFrenchDatumInternational.parseFile
  private val rawUsaData = FamaFrenchDatumUsa.parseFile

  // Construct the above from the raw data below.
  private def makeMetricsMap : TreeMap[Month, FamaFrenchMetrics] = {
    var result = TreeMap[Month, FamaFrenchMetrics]()
    
    rawData foreach { datum =>
      result += (datum.month -> datum.metrics)
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