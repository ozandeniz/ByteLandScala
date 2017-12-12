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
      while (!checkAllyStatusForAllCities(byteLand)) {
        uniteAvailableCities(byteLand)
        resetEnvoyCountForAllCities(byteLand)
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

      for (adjacentCityId <- currentCity.adjacentCityIds) {
        val adjacentCity = byteLand.cityMap(adjacentCityId)
        negotiate(currentCity, adjacentCity)
      }
    }
  }

  def negotiate(currentCity: ByteLandCity, adjacentCity: ByteLandCity): Unit = {
    if (!currentCity.allyCityIds.contains(adjacentCity.cityId)) {
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

    newAdjacentList ++= currentCityAdjacentList ++ adjacentCityAdjacentList

    currentCity.addAllyCity(adjacentCity.cityId)
    adjacentCity.addAllyCity(currentCity.cityId)

    currentCity.enemyCityCount -= 1
    adjacentCity.enemyCityCount -= 1

    currentCity.adjacentCityIds = newAdjacentList - currentCity.cityId
    adjacentCity.adjacentCityIds = newAdjacentList - adjacentCity.cityId
  }

  def resetEnvoyCountForAllCities(byteLand: ByteLand): Unit = {
    for (cityId <- byteLand.cityMap.keySet) {
      byteLand.cityMap(cityId).envoyCount = 1
    }
  }

  def checkAllyStatusForAllCities(byteLand: ByteLand): Boolean = {
    for (cityId <- byteLand.cityMap.keySet) {
      if (byteLand.cityMap(cityId).enemyCityCount != 0)
        return false
    }
    true
  }
}