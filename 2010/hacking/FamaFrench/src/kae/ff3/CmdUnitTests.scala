package kae.ff3

object CmdUnitTests {
  def main(args : Array[String]) : Unit = {

    // FileLocater
    println(FileLocater.locateProjectRoot)
    require(FileLocater.locateProjectRoot.isDirectory)
	  
    // FamaFrenchUsaMonthlyDatum
    FamaFrenchUsaMonthlyDatum.parseFile
    
    // FamaFrenchDigestUsaMonthly
    val digest = FamaFrenchDigestUsaMonthly.createFromFile
    println(digest.earliestMonth)
    println(digest.latestMonth)
    
    // MutualFundMonthlyReturnHistory
    MutualFundMonthlyReturnHistory.createFromFile("dfvex")

  }
}
