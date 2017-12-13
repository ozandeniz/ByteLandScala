package util

import java.security.InvalidParameterException
import land.{ByteLand, ByteLandCity}
import scala.collection.mutable.ListBuffer
import scala.io.Source

object readFile {
  val CONNECTION_INFO_SEPARATOR = " "

  def parseByteLandData(data: String, cityCount: Int): ByteLand = {
    if (data.isEmpty)
      throw new InvalidParameterException("Connections data cannot be empty.")

    val byteLand: ByteLand = new ByteLand
    val connections: Array[Int] = data.split(CONNECTION_INFO_SEPARATOR).map(_.toInt)
    val enemyCityCountAtFirst: Int = cityCount - 1

    if (cityCount != connections.length + 1)
      throw new InvalidParameterException("Invalid city count(" + cityCount + ") or connection count(" + connections.length + ")")

    for (j <- 0 until cityCount - 1) {
      val city1Id: Int = connections(j)
      val city2Id: Int = j + 1

      val city1: ByteLandCity = new ByteLandCity(city1Id, enemyCityCountAtFirst)
      city1.addAdjacentCity(city2Id)

      val city2: ByteLandCity = new ByteLandCity(city2Id, enemyCityCountAtFirst)
      city2.addAdjacentCity(city1Id)

      byteLand.addNewCity(city1)
      byteLand.addNewCity(city2)
    }
    byteLand
  }

  def readByteLandInfo(fileName: String): ListBuffer[ByteLand] = {
    val source = Source.fromFile(fileName)
    val br = source.bufferedReader()

    val byteLands: ListBuffer[ByteLand] = ListBuffer()
    val landCount: Int = br.readLine().toInt

    for (_ <- 0 until landCount) {
      val cityCount: Int = br.readLine().toInt
      val byteLandData = br.readLine()
      byteLands += parseByteLandData(byteLandData, cityCount)
    }

    source.close()
    byteLands
  }
}