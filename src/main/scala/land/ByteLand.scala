package land

class ByteLand {
  var cityMap: collection.mutable.Map[Integer, ByteLandCity] = collection.mutable.HashMap()

  def unionCitiesBasedOnAdjacency(c1: ByteLandCity, c2: ByteLandCity): Unit = {
    var adjacentCitySet = c1.adjacentCityIds
    adjacentCitySet ++= c2.adjacentCityIds

    c1.adjacentCityIds = adjacentCitySet
    c2.adjacentCityIds = adjacentCitySet
  }

  def addNewCity(city: ByteLandCity): Unit = {
    if (cityMap.contains(city.cityId))
      unionCitiesBasedOnAdjacency(city, cityMap(city.cityId))

    cityMap(city.cityId) = city
  }
}