package kae.ff3

object CmdMakeSpreadsheets {
  def main(args : Array[String]) : Unit = {

//  MutualFundMonthlyReturnHistory.createFromFile("DFVQX", true).generateRegressionSpreadsheet	  
//  MutualFundMonthlyReturnHistory.createFromFile("DFIVX", true).generateRegressionSpreadsheet	  
//  MutualFundMonthlyReturnHistory.createFromFile("EFV", true).generateRegressionSpreadsheet	  
//  MutualFundMonthlyReturnHistory.createFromFile("VEU", true).generateRegressionSpreadsheet	  
//  MutualFundMonthlyReturnHistory.createFromFile("VINEX", true).generateRegressionSpreadsheet	  
//	MutualFundMonthlyReturnHistory.createFromFile("DFISX", true).generateRegressionSpreadsheet

	MutualFundMonthlyReturnHistory.createFromFile("VISVX", false).generateRegressionSpreadsheet
	MutualFundMonthlyReturnHistory.createFromFile("VMVIX", false).generateRegressionSpreadsheet
//	MutualFundMonthlyReturnHistory.createFromFile("BRSIX", false).generateRegressionSpreadsheet
//	MutualFundMonthlyReturnHistory.createFromFile("DFFVX", false).generateRegressionSpreadsheet
//	MutualFundMonthlyReturnHistory.createFromFile("DFVEX", false).generateRegressionSpreadsheet
//	MutualFundMonthlyReturnHistory.createFromFile("VBR", false).generateRegressionSpreadsheet
//	MutualFundMonthlyReturnHistory.createFromFile("VOE", false).generateRegressionSpreadsheet
//	MutualFundMonthlyReturnHistory.createFromFile("VTI", false).generateRegressionSpreadsheet
  }
}
