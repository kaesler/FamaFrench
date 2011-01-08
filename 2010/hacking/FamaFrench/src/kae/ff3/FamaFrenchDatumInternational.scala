package kae.ff3
import scala.io.Source

class FamaFrenchDatumIntarnational(
  val month: Month,
  val metrics: FamaFrenchMetrics
) extends Ordered[FamaFrenchDatumUsa]
{
//  // TODO: hashCode and equals
//
//  // Order by date
//  def compare(that: FamaFrenchDatumUsa) : Int = {
//    month.compare(that.month)
//  }
}

object FamaFrenchDatumInternational
{
//  // This file was created by CMPT_ME_BEME_RETS using the 201011 CRSP database.
//  // The 1-month TBill return is from Ibbotson and Associates, Inc.
//  //
//  //        Mkt-RF     SMB     HML      RF
//  //192607    2.62   -2.16   -2.92    0.22
//  private val lineRegexp = """([0-9][0-9][0-9][0-9][0-9][0-9]) +([^ ]+) +([^ ]+) +([^ ]+) +([^ ]+)""".r
//
//  def matches(line: String) : Boolean = {
//    lineRegexp findFirstIn line match {
//      case Some(s) => true
//      case None => false  
//    }
//  }
//  
//  def create(line: String) : FamaFrenchDatumUsa = {
//    val lineRegexp(s1, s2, s3, s4, s5) = line
//    new FamaFrenchDatumUsa(new Month(s1),
//    		               new FamaFrenchMetrics(s2.toDouble,
//    		            		                 s3.toDouble,
//    		            		                 s4.toDouble,	
//    		            		                 s5.toDouble))
//  }
//  
//  def parseFile : Seq[FamaFrenchDatumUsa] = {
//    val file = FileLocater.locateUsaResearchMonthlyDataFile
//    require(file.exists)
//    
//   	(Source.fromFile(file).getLines()
//     // Drop the leading few lines that are not monthly data lines
//     dropWhile { line => !matches(line) }
//
//     // Retain the monthly data lines
//     takeWhile { line => matches(line) }
//
//     // Foreach monthly data line create a datum
//     map { monthlyDataLine => create(monthlyDataLine) }).toSeq
//  }
}
