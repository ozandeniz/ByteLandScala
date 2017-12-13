package negotiation

import java.io.{File, PrintWriter}
import land.{ByteLand, ByteLandCity}
import util.readFile
import scala.collection.mutable.ListBuffer

object unite {
  def main(args: Array[String]): Unit = {
    val inputFileName: String = args(0)
    val outputFileName: String = args(1)
    val byteLands = readFile.readByteLandInfo(inputFileName)

    startNegotiation(byteLands, outputFileName)
  }

  def startNegotiation(byteLands: ListBuffer[ByteLand], outputFileName: String): Unit = {
    var turnCount = 0
    val writer = new PrintWriter(new File(outputFileName))

    for (byteLand <- byteLands) {
      while (!byteLand.checkAllyStatusForAllCities()) {
        uniteAvailableCities(byteLand)

        byteLand.resetEnvoyCountForAllCities()
        turnCount += 1
      }
      writer.println(turnCount)
      turnCount = 0
    }
    writer.close()
  }

  def uniteAvailableCities(byteLand: ByteLand): Unit = {
    for (currentCityId <- byteLand.cityMap.keySet) {
      val currentCity = byteLand.cityMap(currentCityId)

      // Iterate over the adjacent cities and try to negotiate them
      for (adjacentCityId <- currentCity.adjacentCityIds) {
        val adjacentCity = byteLand.cityMap(adjacentCityId)
        negotiate(currentCity, adjacentCity)
      }
    }
  }

  def negotiate(currentCity: ByteLandCity, adjacentCity: ByteLandCity): Unit = {
    if (!currentCity.allyCityIds.contains(adjacentCity.cityId)) {

      // Check the envoy count
      if (currentCity.envoyCount != 0) {
        currentCity.envoyCount = 0
        updateDiplomaticRelationShips(currentCity, adjacentCity)
      }
      else if (adjacentCity.envoyCount != 0) {
        adjacentCity.envoyCount = 0
        updateDiplomaticRelationShips(currentCity, adjacentCity)
      }
    }
  }

  def updateDiplomaticRelationShips(currentCity: ByteLandCity, adjacentCity: ByteLandCity): Unit = {
    val currentCityAdjacentList = currentCity.adjacentCityIds
    val adjacentCityAdjacentList = adjacentCity.adjacentCityIds
    var newAdjacentList: Set[Int] = Set()

    // There is an alliance, can be new adjacent cities
    newAdjacentList ++= currentCityAdjacentList ++ adjacentCityAdjacentList

    // Add as ally city
    currentCity.addAllyCity(adjacentCity.cityId)
    adjacentCity.addAllyCity(currentCity.cityId)

    // Decrease enemy count, they are ally
    currentCity.enemyCityCount -= 1
    adjacentCity.enemyCityCount -= 1

    // We should remove related city Id
    currentCity.adjacentCityIds = newAdjacentList - currentCity.cityId

    // We should remove related city Id
    adjacentCity.adjacentCityIds = newAdjacentList - adjacentCity.cityId
  }
}