package kae.ff3

/**
 * Class to encapsulate the monthly total return history for a mutual fund or ETF.
 */
class MutualFundMonthlyReturnHistory(
  val ticker: String,
  val returns: Map[Month, Double]
)
{
  def validate = {
    // todo: check no gaps etc
  }
  
  def earliestMonth : Month = {
	// todo
  }

  def latestMonth : Month = {
	// todo
  }
  
  def monthCount : Int = {
	// todo
  }
}

object MutualFundMonthlyReturnHistory
{
  def create(yahooData: Seq[YahooDailyPriceDatum]) : MutualFundMonthlyReturnHistory = {
    // todo
	//  - sort the data in ascending data
	//  - validate in any way we can
	//  - need to discard the first month
	//  - compute monthly returns
  }
}
