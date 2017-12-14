import land.{ByteLand, ByteLandCity}
import negotiation.diplomacy
import org.scalatest.{BeforeAndAfter, FunSuite}
import util.readFile

import scala.collection.mutable.ListBuffer

class DiplomacyTest extends FunSuite with BeforeAndAfter {
  test("runDiplomacy1") {
    val byteLand: ByteLand = readFile.parseByteLandData("0 1 2", 4)
    val diplomacyReport:ListBuffer[Int] = diplomacy.runDiplomacy(ListBuffer(byteLand))

    assert(diplomacyReport.head==2)
  }

  test("runDiplomacy2") {
    val byteLand = readFile.parseByteLandData("0 1 2 0 0 3 3", 8)
    val diplomacyReport = diplomacy.runDiplomacy(ListBuffer(byteLand))

    assert(diplomacyReport.head==4)
  }

  test("runDiplomacy3") {
    val byteLand = readFile.parseByteLandData("0 1 1 1 1 0 2 2", 9)
    val diplomacyReport = diplomacy.runDiplomacy(ListBuffer(byteLand))

    assert(diplomacyReport.head==5)
  }

  test("updateDiplomaticRelationShips") {
    val byteLandCity:ByteLandCity = new ByteLandCity(1,1)
    byteLandCity.addAdjacentCity(2)
    byteLandCity.addAdjacentCity(3)
    byteLandCity.enemyCityCount=2

    val byteLandCity1:ByteLandCity = new ByteLandCity(2,1)
    byteLandCity1.enemyCityCount=2
    byteLandCity1.addAdjacentCity(1)

    diplomacy.updateDiplomaticRelationShips(byteLandCity, byteLandCity1)

    assert(byteLandCity.enemyCityCount==1)
    assert(byteLandCity1.enemyCityCount==1)

    assert(byteLandCity.allyCityIds==Set(2))
    assert(byteLandCity1.allyCityIds==Set(1))

    assert(byteLandCity.adjacentCityIds==Set(2, 3))
    assert(byteLandCity1.adjacentCityIds==Set(1, 3))
  }
}