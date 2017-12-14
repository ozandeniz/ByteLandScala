package util

import java.io.{File, PrintWriter}

import scala.collection.mutable.ListBuffer

object writeFile {
  def writeNegotiationReport(fileName: String, negotiationReport:ListBuffer[Int])={
    val writer = new PrintWriter(new File(fileName))

    for(nReport <- negotiationReport){
      writer.println(nReport)
    }

    writer.close()
  }
}