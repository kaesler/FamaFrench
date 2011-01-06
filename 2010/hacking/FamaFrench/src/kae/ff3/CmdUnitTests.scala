package kae.ff3

object CmdUnitTests {
  def main(args : Array[String]) : Unit = {

    // FileLocater
    println(FileLocater.locateProjectRoot)
    require(FileLocater.locateProjectRoot.isDirectory)
	  
    // FamaFrenchUsaMonthlyDatum
    FamaFrenchUsaMonthlyDatum.parseFile
    
    // FamaFrenchDigestUsaMonthly
    println(FamaFrenchDigestUsaMonthly.earliestMonth)
    println(FamaFrenchDigestUsaMonthly.latestMonth)
    
    // MutualFundMonthlyReturnHistory
    MutualFundMonthlyReturnHistory.createFromFile("dffvx")
    MutualFundMonthlyReturnHistory.createFromFile("dfisx")
    MutualFundMonthlyReturnHistory.createFromFile("dfivx")
    MutualFundMonthlyReturnHistory.createFromFile("dfvex")
    MutualFundMonthlyReturnHistory.createFromFile("dfvqx")
  }
}
