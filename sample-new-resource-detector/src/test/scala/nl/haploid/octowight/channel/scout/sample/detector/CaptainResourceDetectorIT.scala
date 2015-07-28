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
    val events = List(TestData.atomChangeEvent(personId1), TestData.atomChangeEvent(personId3))
    val captainsByPersonId = captainResourceDetector.findCaptainsByPersonId(events)
    captainsByPersonId should have size 2
    captainsByPersonId.keySet should contain(personId1)
    captainsByPersonId.keySet should contain(personId2)
  }

  it should "detect captains" in { implicit session =>
    val event1 = TestData.atomChangeEvent(personId1)
    val event2 = TestData.atomChangeEvent(personId3)
    val events = List(event1, event2)
    val actualResourceRoots = captainResourceDetector.detect(events)
    actualResourceRoots should have size 1
    actualResourceRoots.map(_.root.id) should be(Set(personId1))
  }
}
