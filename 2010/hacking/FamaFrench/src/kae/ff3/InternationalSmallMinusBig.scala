package kae.ff3

import java.text.SimpleDateFormat
import java.util.Calendar

import scala.collection.immutable.TreeMap
import scala.io.Source

object InternationalSmallMinusBig
{
  // File looks like
  // Date,SmB
  // Jan 2001,2.770887632
  // Feb 2001,5.707068311

  private val dataLineRegex = """(... [0-9][0-9][0-9][0-9]),([^,]+)""".r
  
  private def matches(line: String) : Boolean = {
    dataLineRegex findFirstIn line match {
      case Some(s) => true
      case None => false  
    }
  }

  def parseFile : TreeMap[Month, Double] = {
    val stream = FileLocater.locateInternationalSmBStream
    
    var result = new TreeMap[Month, Double]()
    
   	(Source.fromInputStream(stream).getLines()

     // Drop the leading few lines that are not monthly data lines
     dropWhile { line => !matches(line) }

     // Retain the monthly data lines
     takeWhile { line => matches(line) }

     // For each monthly data line create a map entry
     foreach { monthlyDataLine =>
       val dataLineRegex(dateField, smallMinusBig) = monthlyDataLine
       val date = new SimpleDateFormat("MMM yyyy").parse(dateField)
       val calendar = Calendar.getInstance
       calendar.setTime(date)
       result += (new Month(calendar) -> smallMinusBig.toDouble)
     })
    return result
  }
}