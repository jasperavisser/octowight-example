package nl.haploid.octowight.sample.repository

import nl.haploid.octowight.sample.{AbstractIT, TestData}
import org.springframework.beans.factory.annotation.Autowired
import scalikejdbc._


class AtomChangeEventDmoRepositoryIT extends AbstractIT {
  @Autowired private[this] val atomChangeEventDmoRepository: AtomChangeEventDmoRepository = null

  behavior of "Atom change event repository"

  override def fixture(implicit session: DBSession): Unit = {
    atomChangeEventDmoRepository.deleteAll()
    atomChangeEventDmoRepository.insert(TestData.atomChangeEventDmo)
    atomChangeEventDmoRepository.insert(TestData.atomChangeEventDmo)
  }

  it should "delete events" in { implicit session =>
    val atomChangeEventDmo = TestData.atomChangeEventDmo
    val inserted = atomChangeEventDmoRepository.insert(atomChangeEventDmo)
    atomChangeEventDmoRepository.findAll() should have size 3
    atomChangeEventDmoRepository.delete(List(inserted))
    atomChangeEventDmoRepository.findAll() should have size 2
  }

  it should "find all" in { implicit session =>
    atomChangeEventDmoRepository.findAll() should have size 2
  }
}
