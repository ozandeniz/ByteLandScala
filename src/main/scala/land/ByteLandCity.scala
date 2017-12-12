package land

class ByteLandCity(val cityId:Int, var enemyCityCount:Int) {
  var envoyCount: Int = 1
  var allyCityIds: Set[Int] = Set()
  var adjacentCityIds: Set[Int] = Set()

  def addAllyCity(cityId: Int): Unit = {
    allyCityIds += cityId
  }

  def addAdjacentCity(cityId: Int): Unit = {
    adjacentCityIds += cityId
  }
}