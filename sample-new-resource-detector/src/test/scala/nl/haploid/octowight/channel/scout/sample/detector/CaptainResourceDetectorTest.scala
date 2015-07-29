package nl.haploid.octowight.channel.scout.sample.detector

import nl.haploid.octowight.channel.scout.sample.{AbstractTest, TestData}
import nl.haploid.octowight.registry.data.{Atom, ResourceRoot}
import nl.haploid.octowight.source.sample.data.CaptainResource
import nl.haploid.octowight.source.sample.repository.{CaptainDmo, CaptainDmoRepository}
import nl.haploid.octowight.{Mocked, Tested}
import org.easymock.EasyMock
import scalikejdbc.DBSession

class CaptainResourceDetectorTest extends AbstractTest {
  @Tested private[this] val captainResourceDetector: CaptainResourceDetector = null
  @Mocked private[this] val captainDmoRepository: CaptainDmoRepository = null

  behavior of "Captain resource captainResourceDetector"

  implicit val session = mock[DBSession]

  it should "have an atom category" in {
    captainResourceDetector.atomCategories should have size 1
  }

  it should "detect captains" in {
    val captainResourceDetector = withMocks(EasyMock.createMockBuilder(classOf[CaptainResourceDetector])
      .addMockedMethod("findCaptainsByPersonId")
      .createMock())
    val event1 = TestData.atomChangeEvent
    val event2 = TestData.atomChangeEvent
    val event3 = TestData.atomChangeEvent
    val atomIds = Set(event1.id, event2.id, event3.id)
    val captainDmo2 = mock[CaptainDmo]
    val captainsByPersonId = Map((event2.id, captainDmo2))
    val resourceRoot = ResourceRoot(Atom(id = event2.id, group = event2.atomGroup), CaptainResource.ResourceCollection)
    val expectedResourceRoots = Set(resourceRoot)
    expecting {
      captainResourceDetector.findCaptainsByPersonId(atomIds) andReturn captainsByPersonId once()
    }
    whenExecuting(captainResourceDetector) {
      val actualResourceRoots = captainResourceDetector.detect(event2.atomGroup, atomIds)
      actualResourceRoots should be(expectedResourceRoots)
    }
  }

  it should "find captains by person id" in {
    val event1 = TestData.atomChangeEvent
    val event2 = TestData.atomChangeEvent
    val atomIds = Set(event1.id, event2.id)
    val id1 = event1.id
    val id2 = event2.id
    val captainDmo1 = mock[CaptainDmo]
    val captainDmo2 = mock[CaptainDmo]
    val captainDmos = List(captainDmo1, captainDmo2)
    expecting {
      captainDmoRepository.findByPersonIds(Set(id1, id2)) andReturn captainDmos once()
      captainDmo1.personId andReturn id1 once()
      captainDmo2.personId andReturn id2 once()
    }
    whenExecuting(captainDmoRepository, captainDmo1, captainDmo2) {
      val captainsByPersonId = captainResourceDetector.findCaptainsByPersonId(atomIds)
      captainsByPersonId.get(id1).orNull should be(captainDmo1)
      captainsByPersonId.get(id2).orNull should be(captainDmo2)
    }
  }
}
