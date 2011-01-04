package kae.ff3

import scala.collection.mutable
import scala.collection.immutable.TreeMap
import scala.io.Source

/**
 * Object to encapsulate Ken French's monthly 3-factor data history.
 */
class FamaFrenchDigestUsaMonthly(
  rawData : Seq[FamaFrenchUsaMonthlyDatum]
)
{
  private val monthlyReturnsMap = makeMonthlyReturnsMap 
  validate

  def earliestMonth : Month = {
	monthlyReturnsMap.firstKey
  }

  def latestMonth : Month = {
    monthlyReturnsMap.lastKey
  }

  def monthCount : Int = {
	monthlyReturnsMap.size
  }

  // Construct the above from the raw data below.
  private def makeMonthlyReturnsMap : TreeMap[Month, FamaFrenchMetrics] = {
    var result = TreeMap[Month, FamaFrenchMetrics]()
    
    rawData foreach { datum =>
      result += (datum.month -> datum.metrics)
    }
    result
  }

  private def validate = {
	// Make sure there are no gaps. I.e. the successor of all the keys except
	// the last one must be contained in the key set.
	val last = latestMonth
	monthlyReturnsMap.keys foreach { month =>
	  if (month != last) {
	    require(monthlyReturnsMap.contains(month.successor))
	  }
	}
  }
}

object FamaFrenchDigestUsaMonthly
{
  def createFromFile : FamaFrenchDigestUsaMonthly = {
    new FamaFrenchDigestUsaMonthly(FamaFrenchUsaMonthlyDatum.parseFile)
  }
}