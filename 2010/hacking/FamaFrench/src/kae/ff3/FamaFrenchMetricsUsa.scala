package kae.ff3
import scala.collection.immutable.TreeMap
import scala.io.Source

object FamaFrenchMetricsUsa
{
  // This file was created by CMPT_ME_BEME_RETS using the 201011 CRSP database.
  // The 1-month TBill return is from Ibbotson and Associates, Inc.
  //
  //        Mkt-RF     SMB     HML      RF
  //192607    2.62   -2.16   -2.92    0.22
  private val lineRegexp = """([0-9][0-9][0-9][0-9][0-9][0-9]) +([^ ]+) +([^ ]+) +([^ ]+) +([^ ]+)""".r

  private def matches(line: String) : Boolean = {
    lineRegexp findFirstIn line match {
      case Some(s) => true
      case None => false  
    }
  }
  
  def parseFile : TreeMap[Month, FamaFrenchMetrics] = {
    val stream = FileLocater.locateUsaResearchMonthlyDataStream
    
    var result = new TreeMap[Month, FamaFrenchMetrics]()
    
   	(Source.fromInputStream(stream).getLines()

     // Drop the leading few lines that are not monthly data lines
     dropWhile { line => !matches(line) }

     // Retain the monthly data lines
     takeWhile { line => matches(line) }

     // For each monthly data line create a map entry
     foreach { monthlyDataLine =>
       val lineRegexp(month,
    		          marketMinusRiskFree,
    		          smallMinusBig,
    		          highMinusLow,
    		          riskFree) = monthlyDataLine
       result += (new Month(month)
                  ->
                  new FamaFrenchMetrics(marketMinusRiskFree.toDouble,
                		                smallMinusBig.toDouble,
                		                highMinusLow.toDouble,
                		                riskFree.toDouble))
     })
    result
  }
}