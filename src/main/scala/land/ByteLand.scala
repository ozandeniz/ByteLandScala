package land

class ByteLand {
  var cityMap: collection.mutable.Map[Integer, ByteLandCity] = collection.mutable.HashMap()

  def updateAdjacency(c1: ByteLandCity, c2: ByteLandCity){
    // Update adjacency of cities
    var adjacentCitySet = c1.adjacentCityIds
    adjacentCitySet ++= c2.adjacentCityIds

    c1.adjacentCityIds = adjacentCitySet
    c2.adjacentCityIds = adjacentCitySet
  }

  def addNewCity(city: ByteLandCity){
    if (cityMap.contains(city.cityId))
      // If the map contains a city update its adjacency with union city
      updateAdjacency(city, cityMap(city.cityId))
    // Add the city to the land
    cityMap(city.cityId) = city
  }

  def resetEnvoyCountForAllCities(){
    for (cityId <- cityMap.keySet) {
      cityMap(cityId).envoyCount = 1
    }
  }

  def checkAllyStatusForAllCities(): Boolean = {
    for (cityId <- cityMap.keySet) {
      if (cityMap(cityId).enemyCityCount != 0)
        return false
    }
    true
  }
}