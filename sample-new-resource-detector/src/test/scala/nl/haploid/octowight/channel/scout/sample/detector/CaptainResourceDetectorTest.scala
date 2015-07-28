package nl.haploid.octowight.channel.scout.sample.detector

import nl.haploid.octowight.channel.scout.sample.{AbstractTest, TestData}
import nl.haploid.octowight.registry.data.ResourceRoot
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
    val events = List(event1, event2, event3)
    val captainDmo1 = mock[CaptainDmo]
    val captainDmo2 = mock[CaptainDmo]
    val captainsByPersonId = Map((event2.atomId, captainDmo2))
    val resourceRoot = ResourceRoot(event2, CaptainResource.ResourceCollection)
    val expectedResourceRoots = Set(resourceRoot)
    expecting {
      captainResourceDetector.findCaptainsByPersonId(events) andReturn captainsByPersonId once()
    }
    whenExecuting(captainResourceDetector) {
      val actualResourceRoots = captainResourceDetector.detect(events)
      actualResourceRoots should be(expectedResourceRoots)
    }
  }

  it should "find captains by person id" in {
    val event1 = TestData.atomChangeEvent
    val event2 = TestData.atomChangeEvent
    val events = List(event1, event2)
    val id1 = event1.atomId
    val id2 = event2.atomId
    val captainDmo1 = mock[CaptainDmo]
    val captainDmo2 = mock[CaptainDmo]
    val captainDmos = List(captainDmo1, captainDmo2)
    expecting {
      captainDmoRepository.findByPersonIds(Set(id1, id2)) andReturn captainDmos once()
      captainDmo1.personId andReturn id1 once()
      captainDmo2.personId andReturn id2 once()
    }
    whenExecuting(captainDmoRepository, captainDmo1, captainDmo2) {
      val captainsByPersonId = captainResourceDetector.findCaptainsByPersonId(events)
      captainsByPersonId.get(id1).orNull should be(captainDmo1)
      captainsByPersonId.get(id2).orNull should be(captainDmo2)
    }
  }
}
