import land.{ByteLand, ByteLandCity}
import org.scalatest.{BeforeAndAfter, FunSuite}

class UniteTest extends FunSuite with BeforeAndAfter {
  test("updateDiplomaticRelationShips") {
    var byteLandCity:ByteLandCity = new ByteLandCity(1,1)
    byteLandCity.enemyCityCount=1
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

  test("negotiate") {
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
}