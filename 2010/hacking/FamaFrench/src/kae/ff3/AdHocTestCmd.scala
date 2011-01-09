package kae.ff3

import java.util.zip.ZipFile
import java.util.zip.ZipEntry
import scala.collection.JavaConversions._

object AdHocTestCmd {
  def main(args : Array[String]) : Unit = {
    val x = FamaFrenchMetricsInternational.parseFile
    x foreach { datum => println(datum) }
  }
}
