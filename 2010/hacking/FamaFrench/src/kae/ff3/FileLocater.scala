package kae.ff3
import java.io.File
import java.io.InputStream
import java.io.FileInputStream
import java.util.zip.ZipFile
import java.util.zip.ZipEntry
import scala.collection.JavaConversions._

object FileLocater
{
  val projectRoot = locateProjectRoot
  
  def locateInternationalSmBStream : InputStream = {
    val file = new File(projectRoot,
    		            "input/international_smb/msci_indices/International_SmB.csv")
    new FileInputStream(file)
  }

  def locateFundDataFile(ticker: String) : File = {
    new File(projectRoot,
    		 "data/input/mutual_fund_daily_price_histories/%s.csv".format(ticker))
  }
	
  def locateFundSpreadsheetFile(ticker: String) : File = {
    new File(projectRoot,
    		 "data/output/spreadsheets/%s_regression.csv".format(ticker))
		
  }
	
  private def locateUsaResearchMonthlyDataZip : File = {
    new File(projectRoot, "data/input/ken_french_research_data/F-F_Research_Data_Factors.zip")
  }

  def locateUsaResearchMonthlyDataStream : InputStream = {
     val zip = new ZipFile(FileLocater.locateUsaResearchMonthlyDataZip)
     zip.entries find { entry => entry.getName.equals("F-F_Research_Data_Factors.txt")} match {
       case Some(e) => zip.getInputStream(e)
       case None => throw new Exception("zip entry not found")
     }
  }

  private def locateInternationalResearchMonthlyDataZip : File = {
    new File(projectRoot, "data/input/ken_french_research_data/F-F_International_Indices.zip")
  }

  def locateInternationalResearchMonthlyDataStream : InputStream = {
     val zip = new ZipFile(FileLocater.locateInternationalResearchMonthlyDataZip)
     zip.entries find { entry => entry.getName.startsWith("Ind_all" )} match {
       case Some(e) => zip.getInputStream(e)
       case None => throw new Exception("zip entry not found")
     }
  }

  private def locateProjectRoot : File = {
    var f = new File(getClass.getResource(".").getPath())
	while (! (new File(f, "data")).isDirectory) {
      f = f.getParentFile
    }
	return f
  }
  
}
