package kae.ff3

import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URL
import scala.io.Source

object CmdFetchFromWeb {
  // TODO: download this and use in zip form
  val ffDailyZip = "http://mba.tuck.dartmouth.edu/pages/faculty/ken.french/ftp/F-F_Research_Data_Factors_daily.zip"
	  
  def urlForTicker(ticker: String) : URL = {
	new URL(
      "http://ichart.finance.yahoo.com/table.csv?s=%s&a=00&b=1&c=1900&d=00&e=8&f=2011&g=d&ignore=.csv".format(ticker)
    )
  }

  /** 
   * Copy an input stream to an output stream.
   * @param input input stream
   * @param output output stream
   */
  private def copy(input: InputStream, output: OutputStream) = {
    val data = new Array[Byte](2048)
    def readData(count: Int): Unit = {
      if (count != -1) {
        output.write(data, 0, count)
        output.flush
        readData(input.read(data, 0, 2048))
      }
    }
    readData(input.read(data, 0, 2048))
  }
  
  def fetchFundPriceHistory(ticker: String) : Unit = {
	copy(urlForTicker(ticker).openStream,
		 new FileOutputStream(FileLocater.locateFundDataFile(ticker)))
  }

  def main(args : Array[String]) : Unit = {
	// Domestic stock
//	fetchFundPriceHistory("VISVX")
	fetchFundPriceHistory("VMVIX")
//    fetchFundPriceHistory("BRSIX")
//    fetchFundPriceHistory("DFFVX")
//    fetchFundPriceHistory("DFVEX")
//	fetchFundPriceHistory("VBR")
//    fetchFundPriceHistory("VOE")
//    fetchFundPriceHistory("VTI")
//    
//    // International stock
	
	fetchFundPriceHistory("SCZ")
//	fetchFundPriceHistory("DFISX")
//	fetchFundPriceHistory("DFIVX")
//	fetchFundPriceHistory("DFVQX")
//	fetchFundPriceHistory("EFV")
//	fetchFundPriceHistory("VEU")
//    fetchFundPriceHistory("VINEX")
//	fetchFundPriceHistory("VSS")
//	fetchFundPriceHistory("VWO")
    
  }
}
