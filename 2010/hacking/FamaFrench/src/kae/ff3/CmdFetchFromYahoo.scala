package kae.ff3

import java.net.URL
import scala.io.Source

object CmdFetchFromYahoo {
  def urlForTicker(ticker: String) : String = {
    "http://ichart.finance.yahoo.com/table.csv?s=%s&a=00&b=1&c=1900&d=00&e=8&f=2011&g=d&ignore=.csv".format(ticker)
  }
  def main(args : Array[String]) : Unit = {
    val url = new URL(urlForTicker("IBM"))
    Source.fromInputStream(url.openStream).getLines.foreach(println)
  }
}
