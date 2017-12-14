package negotiation

import util.{readFile, writeFile}

import scala.collection.mutable.ListBuffer

object negotiator {
  def main(args: Array[String]): Unit = {
    val inputFileName: String = args(0)
    val outputFileName: String = args(1)
    val byteLands = readFile.readByteLandInfo(inputFileName)

    val diplomacyReport:ListBuffer[Int] = diplomacy.runDiplomacy(byteLands)

    writeFile.writeNegotiationReport(outputFileName, diplomacyReport)
  }
}