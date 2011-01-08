package kae.ff3

import scala.collection.mutable
import scala.collection.immutable.TreeMap
import scala.io.Source

/**
 * Object to encapsulate Ken French's monthly 3-factor data history.
 */
object FamaFrenchDigestUsaMonthly
{
  private val rawData = FamaFrenchUsaMonthlyDatum.parseFile

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
	val last = latestMonth
	metricsMap.keys foreach { month =>
	  if (month != last) {
	    require(metricsMap.contains(month.successor))
	  }
	}
  }

  def getMetrics (month: Month) : FamaFrenchMetrics = metricsMap(month)

  def earliestMonth : Month = {
	metricsMap.firstKey
  }

  def months  = metricsMap.keySet

  def latestMonth : Month = {
    metricsMap.lastKey
  }

  def monthCount : Int = {
	metricsMap.size
  }
}