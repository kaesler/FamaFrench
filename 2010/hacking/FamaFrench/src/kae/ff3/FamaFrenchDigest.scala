package kae.ff3
import scala.collection.SortedSet

 /**
  * Class encapsulating the FamaFrench research data on stock returns.
  */
abstract class FamaFrenchDigest
{
  /**
   * Get metrics for a given month
   * @param month the month
   * @return the metrics
   */
  def getMetrics (month: Month) : FamaFrenchMetrics
  
  /**
   * Get the months covered by this digest.
   * @return the set of months
   */
  def months : SortedSet[Month]
}