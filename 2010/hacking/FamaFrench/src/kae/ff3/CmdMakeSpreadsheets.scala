package kae.ff3

object CmdMakeSpreadsheets {
  def main(args : Array[String]) : Unit = {
	  
	MutualFundMonthlyReturnHistory.createFromFile("BRSIX").generateRegressionSpreadsheet
	MutualFundMonthlyReturnHistory.createFromFile("DFFVX").generateRegressionSpreadsheet
	MutualFundMonthlyReturnHistory.createFromFile("DFVEX").generateRegressionSpreadsheet
	MutualFundMonthlyReturnHistory.createFromFile("VBR").generateRegressionSpreadsheet
	MutualFundMonthlyReturnHistory.createFromFile("VOE").generateRegressionSpreadsheet
	MutualFundMonthlyReturnHistory.createFromFile("VTI").generateRegressionSpreadsheet
  }
}
