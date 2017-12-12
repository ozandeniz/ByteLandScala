package util

import land.{ByteLand, ByteLandCity}
import scala.collection.mutable.ListBuffer
import scala.io.Source

object readFile {
  val CONNECTION_INFO_SEPARATOR = " "

  def readByteLandInfo(fileName: String): ListBuffer[ByteLand] = {
    val source = Source.fromFile(fileName)
    val br = source.bufferedReader()

    val byteLands: ListBuffer[ByteLand] = ListBuffer()
    val landCount: Int = br.readLine().toInt

    for (i <- 0 until landCount) {
      val cityCount: Int = br.readLine().toInt
      val enemyCityCountAtFirst: Int = cityCount - 1
      val byteLand: ByteLand = new ByteLand

      val connectionInfo: String = br.readLine
      val connections: Array[Int] = connectionInfo.split(CONNECTION_INFO_SEPARATOR).map(_.toInt)

      for (j <- 0 until cityCount-1) {
        val city1Id: Int = connections(j)
        val city2Id: Int = j + 1

        val city1: ByteLandCity = new ByteLandCity(city1Id, enemyCityCountAtFirst)
        city1.addAdjacentCity(city2Id)

        val city2: ByteLandCity = new ByteLandCity(city2Id, enemyCityCountAtFirst)
        city2.addAdjacentCity(city1Id)

        byteLand.addNewCity(city1)
        byteLand.addNewCity(city2)
      }
      byteLands+=byteLand
    }

    source.close()
    byteLands
  }
}