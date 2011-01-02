package kae.ff3
import java.io.File

object FileLocater
{
  val projectRoot = locateProjectRoot
  
  def locateFundDataFile(ticker: String) : File = {
    new File(projectRoot,
    		 "data/input/mutual_fund_daily_price_histories/%s.csv".format(ticker))
  }
	
  def locateFundSpreadsheetFile(ticker: String) : File = {
    new File(projectRoot,
    		 "data/output/spreadsheets/%s_regression.csv".format(ticker))
		
  }
	
  def locateUsaResearchMonthlyDataFile : File = {
    new File(projectRoot, "data/input/ken_french_research_data/F-F_Research_Data_Factors.txt")
  }

  def locateProjectRoot : File = {
    var f = new File(getClass.getResource(".").getPath())
	while (! (new File(f, "data")).isDirectory) {
      f = f.getParentFile
    }
	return f
  }
  
}
