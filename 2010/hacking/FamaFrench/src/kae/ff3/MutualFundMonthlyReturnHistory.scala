package kae.ff3
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
    yahooData: Seq[YahooDailyPriceDatum]
  ) : MutualFundMonthlyReturnHistory = {
    val map = new TreeMap[Month, Double]()

    // todo
	//  - sort the data in ascending data
	//  - validate in any way we can
	//  - need to discard the first month
	//  - compute monthly returns from daily data
	  

    new MutualFundMonthlyReturnHistory(ticker, map)
  }
}
