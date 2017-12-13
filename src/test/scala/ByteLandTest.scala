import land.{ByteLand, ByteLandCity}
import org.scalatest.{BeforeAndAfter, FunSuite}

class ByteLandTest extends FunSuite with BeforeAndAfter {
  test("updateAdjacency") {
    var byteLandCity:ByteLandCity = new ByteLandCity(1,1)
    byteLandCity.addAdjacentCity(2)
    byteLandCity.addAdjacentCity(3)

    var byteLandCity1:ByteLandCity = new ByteLandCity(2,1)
    byteLandCity1.addAdjacentCity(1)

    var byteLandCity2:ByteLandCity = new ByteLandCity(3,1)
    byteLandCity2.addAdjacentCity(1)

    var byteLand: ByteLand = new ByteLand
    byteLand.cityMap.put(1, byteLandCity)
    byteLand.cityMap.put(2, byteLandCity1)
    byteLand.cityMap.put(3, byteLandCity2)

    byteLand.updateAdjacency(byteLandCity, byteLandCity1)
    assert(byteLandCity.adjacentCityIds==Set(1,2,3))
    assert(byteLandCity1.adjacentCityIds==Set(1,2,3))
  }

  test("addNewCity") {
    var byteLandCity:ByteLandCity = new ByteLandCity(1,1)
    byteLandCity.addAdjacentCity(1)

    var byteLandCity1:ByteLandCity = new ByteLandCity(1,1)
    byteLandCity.addAdjacentCity(2)
    byteLandCity.addAdjacentCity(3)

    var byteLand: ByteLand = new ByteLand
    byteLand.addNewCity(byteLandCity)
    byteLand.addNewCity(byteLandCity1)

    assert(byteLandCity.adjacentCityIds==Set(1,2,3))
  }

  test("resetEnvoyCountForAllCities") {
    var byteLandCity:ByteLandCity = new ByteLandCity(1,0)
    var byteLandCity1:ByteLandCity = new ByteLandCity(2,0)
    var byteLandCity2:ByteLandCity = new ByteLandCity(3,0)

    var byteLand: ByteLand = new ByteLand

    byteLand.addNewCity(byteLandCity)
    byteLand.addNewCity(byteLandCity1)
    byteLand.addNewCity(byteLandCity2)

    assert(byteLandCity.envoyCount==1)
    assert(byteLandCity1.envoyCount==1)
    assert(byteLandCity2.envoyCount==1)
  }

  test("checkAllyStatusForAllCities1") {
    var byteLandCity:ByteLandCity = new ByteLandCity(1,1)
    byteLandCity.enemyCityCount=0
    var byteLandCity1:ByteLandCity = new ByteLandCity(2,1)
    byteLandCity1.enemyCityCount=0
    var byteLandCity2:ByteLandCity = new ByteLandCity(3,1)
    byteLandCity2.enemyCityCount=0

    var byteLand: ByteLand = new ByteLand

    byteLand.addNewCity(byteLandCity)
    byteLand.addNewCity(byteLandCity1)
    byteLand.addNewCity(byteLandCity2)

    assert(byteLand.checkAllyStatusForAllCities())
  }

  test("checkAllyStatusForAllCities2") {
    var byteLandCity:ByteLandCity = new ByteLandCity(1,1)
    byteLandCity.enemyCityCount=0
    var byteLandCity1:ByteLandCity = new ByteLandCity(2,1)
    byteLandCity1.enemyCityCount=1
    var byteLandCity2:ByteLandCity = new ByteLandCity(3,1)
    byteLandCity2.enemyCityCount=0

    var byteLand: ByteLand = new ByteLand

    byteLand.addNewCity(byteLandCity)
    byteLand.addNewCity(byteLandCity1)
    byteLand.addNewCity(byteLandCity2)

    assert(!byteLand.checkAllyStatusForAllCities())
  }
}