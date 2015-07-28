package nl.haploid.octowight.source.sample.repository

import nl.haploid.octowight.source.sample.{AbstractIT, TestData}
import org.springframework.beans.factory.annotation.Autowired
import scalikejdbc._

class CaptainDmoRepositoryIT extends AbstractIT {
  behavior of "Captain DMO repository"

  @Autowired private[this] val captainDmoRepository: CaptainDmoRepository = null

  val personId1 = TestData.nextLong
  val personId2 = TestData.nextLong
  val personId3 = TestData.nextLong

  override def fixture(implicit session: DBSession): Unit = {
    sql"delete from octowight.role".update().apply()
    sql"delete from octowight.person".update().apply()
    sql"insert into octowight.person(id, name) values($personId1, ${TestData.nextString})".update().apply()
    sql"insert into octowight.person(id, name) values($personId2, ${TestData.nextString})".update().apply()
    sql"insert into octowight.person(id, name) values($personId3, ${TestData.nextString})".update().apply()
    sql"insert into octowight.role(person, name) values($personId1, 'captain')".update().apply()
    sql"insert into octowight.role(person, name) values($personId2, 'captain')".update().apply()
    sql"insert into octowight.role(person, name) values($personId3, 'deckhand')".update().apply()
  }

  it should "find captains by person ids" in { implicit session =>
    val personIds = Set(personId1, personId2, personId3)
    val actualCaptainDmos: List[CaptainDmo] = captainDmoRepository.findByPersonIds(personIds)
    actualCaptainDmos should have size 2
  }
}
