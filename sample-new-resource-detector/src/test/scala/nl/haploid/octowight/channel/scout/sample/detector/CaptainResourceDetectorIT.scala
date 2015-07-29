package nl.haploid.octowight.channel.scout.sample.detector

import nl.haploid.octowight.channel.scout.sample.{AbstractIT, TestData}
import org.springframework.beans.factory.annotation.Autowired
import scalikejdbc._

class CaptainResourceDetectorIT extends AbstractIT {
  @Autowired private[this] val captainResourceDetector: CaptainResourceDetector = null

  behavior of "Captain resource captainResourceDetector"

  val personId1 = TestData.nextLong
  val personId2 = TestData.nextLong
  val personId3 = TestData.nextLong

  override def fixture(implicit session: DBSession): Unit = {
    sql"delete from octowight.role".update().apply()
    sql"delete from octowight.person".update().apply()
    sql"insert into octowight.person(id, name) values($personId1, ${TestData.nextString})".update().apply()
    sql"insert into octowight.person(id, name) values($personId2, ${TestData.nextString})".update().apply()
    sql"insert into octowight.person(id, name) values($personId3, ${TestData.nextString})".update().apply()
    sql"insert into octowight.role(person, name) values($personId1, ${CaptainResourceDetector.RoleType})".update().apply()
    sql"insert into octowight.role(person, name) values($personId2, ${CaptainResourceDetector.RoleType})".update().apply()
    sql"insert into octowight.role(person, name) values($personId3, 'deckhand')".update().apply()
  }

  it should "find captains by person id" in { implicit session =>
    val event1 = TestData.atomChangeEvent(personId1)
    val event2 = TestData.atomChangeEvent(personId3)
    val atomIds = Set(event1.id, event2.id)
    val captainsByPersonId = captainResourceDetector.findCaptainsByPersonId(atomIds)
    captainsByPersonId should have size 2
    captainsByPersonId.keySet should contain(personId1)
    captainsByPersonId.keySet should contain(personId2)
  }

  it should "detect captains" in { implicit session =>
    val event1 = TestData.atomChangeEvent(personId1)
    val event2 = TestData.atomChangeEvent(personId3)
    val atomIds = Set(event1.id, event2.id)
    val actualResourceRoots = captainResourceDetector.detect(event1.atomGroup, atomIds)
    actualResourceRoots should have size 1
    actualResourceRoots.map(_.root.id) should be(Set(personId1))
  }
}
