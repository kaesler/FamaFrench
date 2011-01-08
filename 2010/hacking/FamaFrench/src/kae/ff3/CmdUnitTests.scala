package kae.ff3

object CmdUnitTests {
  def main(args : Array[String]) : Unit = {

	val x = YahooPriceDatum.parseFile("dfvex")
	val y = x map { datum => datum.month }
	val z = y.toSeq.distinct
	val aug2009 = new Month(2009,8)
	require(y.toSeq.contains(aug2009))
	require(z.contains(aug2009))
	
    // FileLocater
    println(FileLocater.locateProjectRoot)
    require(FileLocater.locateProjectRoot.isDirectory)
	  
    // FamaFrenchUsaMonthlyDatum
    FamaFrenchUsaMonthlyDatum.parseFile
    
    // FamaFrenchDigestUsaMonthly
    println(FamaFrenchDigestUsaMonthly.earliestMonth)
    println(FamaFrenchDigestUsaMonthly.latestMonth)
    
    // MutualFundMonthlyReturnHistory
//    MutualFundMonthlyReturnHistory.createFromFile("dffvx")
//    MutualFundMonthlyReturnHistory.createFromFile("dfisx")
//    MutualFundMonthlyReturnHistory.createFromFile("dfivx")
//    MutualFundMonthlyReturnHistory.createFromFile("dfvqx")

    val dfvexHistory = MutualFundMonthlyReturnHistory.createFromFile("dfvex")
    dfvexHistory.generateRegressionSpreadsheet

    val dffvxHistory = MutualFundMonthlyReturnHistory.createFromFile("dffvx")
    dffvxHistory.generateRegressionSpreadsheet
}
}
