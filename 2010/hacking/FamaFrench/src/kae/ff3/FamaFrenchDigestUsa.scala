package kae.ff3

import scala.collection.mutable
import scala.collection.immutable.TreeMap
import scala.io.Source

/**
 * Object to encapsulate Ken French's monthly 3-factor data history.
 */
object FamaFrenchDigestUsa extends FamaFrenchDigest
{
  private val metricsMap = FamaFrenchMetricsUsa.parseFile

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
  
  def riskFreeReturns : Map[Month, Double] = {
    metricsMap mapValues { metrics => metrics.riskFree }
  }
}