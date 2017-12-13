import java.security.InvalidParameterException

import land.ByteLand
import org.scalatest.{BeforeAndAfter, FunSuite}
import util.readFile

class ReadFileTest extends FunSuite with BeforeAndAfter {

  var data1:String= _
  var cityCount1:Int = _
  var cityCount2:Int= _
  var data3:String= _

  before {
    cityCount1 = 4
    cityCount2 = 5

    data1 = "0 1 2"
    data3 = ""
  }

  test("Byte Land data should be parsed well") {
    val byteLand: ByteLand = readFile.parseByteLandData(data1, cityCount1)
    assert(byteLand.cityMap.size==cityCount1)
    assert(byteLand.cityMap(0).cityId==0)
    assert(byteLand.cityMap(1).cityId==1)
    assert(byteLand.cityMap(2).cityId==2)
    assert(byteLand.cityMap(3).cityId==3)

    assert(byteLand.cityMap(0).enemyCityCount==3)
    assert(byteLand.cityMap(1).enemyCityCount==3)
    assert(byteLand.cityMap(2).enemyCityCount==3)
    assert(byteLand.cityMap(3).enemyCityCount==3)

    assert(byteLand.cityMap(0).envoyCount==1)
    assert(byteLand.cityMap(1).envoyCount==1)
    assert(byteLand.cityMap(2).envoyCount==1)
    assert(byteLand.cityMap(3).envoyCount==1)

    assert(byteLand.cityMap(0).allyCityIds.isEmpty)
    assert(byteLand.cityMap(1).allyCityIds.isEmpty)
    assert(byteLand.cityMap(2).allyCityIds.isEmpty)
    assert(byteLand.cityMap(3).allyCityIds.isEmpty)

    assert(byteLand.cityMap(0).adjacentCityIds==Set(1))
    assert(byteLand.cityMap(1).adjacentCityIds==Set(2,0))
    assert(byteLand.cityMap(2).adjacentCityIds==Set(3,1))
    assert(byteLand.cityMap(3).adjacentCityIds==Set(2))
  }

  test("City count mismatch") {
    assertThrows[InvalidParameterException] {
      val byteLand: ByteLand = readFile.parseByteLandData(data1, cityCount2)
    }
  }

  test("Empty connection data") {
    assertThrows[InvalidParameterException] {
      val byteLand: ByteLand = readFile.parseByteLandData(data3, cityCount1)
    }
  }
}