package kae.ff3

/**
 * Class to represent the Fama-French metrics for a given period.
 */
class FamaFrenchMetrics (
  val marketMinusRiskFree: Double,
  val smallMinusBig: Double,
  val highMinusLow: Double,
  val riskFree: Double
){

}