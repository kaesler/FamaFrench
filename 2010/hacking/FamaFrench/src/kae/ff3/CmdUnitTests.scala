package kae.ff3

object CmdUnitTests {
  def main(args : Array[String]) : Unit = {

    // FileLocater
    println(FileLocater.locateProjectRoot)
    require(FileLocater.locateProjectRoot.isDirectory)
	  
    // FrenchDataUsaMonthly
    val ff = FrenchDataUsaMonthly.createFromFile
    println(ff.earliestMonth)
    println(ff.latestMonth)
    val x = YahooDailyPriceDatum
  }
}
