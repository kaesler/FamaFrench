package kae.ff3

import scala.io.Source
import scala.util.matching.Regex
import scala.collection.immutable.TreeMap

class FamaFrenchMetricsInternational(
  val marketReturn: Double,
  val highMinusLow: Double
)
{
  override def toString : String = {
    "%f, %f".format(marketReturn, highMinusLow)
  }
}

object FamaFrenchMetricsInternational
{
  // This is the section of the file we're interested in:
  //
  //     Value-Weight Dollar Returns      All 4 Data Items Required
  //                -- BE/ME --   --- E/P ---   --- CE/P --   ------  Yld  -----
  //          Mkt   High    Low   High    Low   High    Low   High    Low   Zero
  //197501  17.39  16.03  17.63  17.12  17.46  16.48  18.42  15.00  18.24   6.73
  //197502  13.59  15.63  12.56  15.92  11.77  15.20  12.67  14.53  12.74   6.28

  val headerLineRegexp = """ +Value-Weight Dollar Returns      All 4 Data Items Required""".r
  val dataLineRegexp = """([0-9][0-9][0-9][0-9][0-9][0-9]) +([^ ]+) +([^ ]+) +([^ ]+) +.*""".r

  def matches(line: String, re: Regex) : Boolean = {
    re findFirstIn line match {
      case Some(s) => true
      case None => false  
    }
  }

  def parseFile : TreeMap[Month, FamaFrenchMetricsInternational] = {
    val stream = FileLocater.locateInternationalResearchMonthlyDataStream
    
    var result = TreeMap[Month, FamaFrenchMetricsInternational]()
    
   	(Source.fromInputStream(stream).getLines()
   			
     // Drop the leading few lines that are not monthly data lines
     dropWhile { line => !matches(line, headerLineRegexp) }
     
   	 // Drop the 3 header lines
   	 drop(3)
   	 
     // Retain the monthly data lines
     takeWhile { line => matches(line, dataLineRegexp) }

     // Foreach monthly data line add a map entry
     foreach { monthlyDataLine => 
       val dataLineRegexp(month, mkt, high, low) = monthlyDataLine
       result += (new Month(month) -> new FamaFrenchMetricsInternational(mkt.toDouble,
    		  		                                                     high.toDouble - low.toDouble))
     })
    result
  }

}
