package nl.haploid.octowight.builder.sample.builder

import nl.haploid.octowight.builder.sample.{AbstractTest, TestData}
import nl.haploid.octowight.model.sample.data.CaptainModel
import nl.haploid.octowight.source.sample.repository._
import nl.haploid.octowight.{JsonMapper, Mocked, Tested}
import scalikejdbc.DBSession

class CaptainResourceBuilderTest extends AbstractTest {
  @Tested private[this] val captainResourceBuilder: CaptainResourceBuilder = null
  @Mocked private[this] val captainDmoRepository: CaptainDmoRepository = null
  @Mocked private[this] val captainModelFactory: CaptainModelFactory = null
  @Mocked private[this] val jsonMapper: JsonMapper = null

  behavior of "Captain resource builder"

  it should "build resources" in {
    implicit val session = mock[DBSession]
    val resourceRoot1 = TestData.resourceRoot
    val resourceRoot2 = TestData.resourceRoot
    val resourceRoots = List(resourceRoot1, resourceRoot2)
    val captainDmo1 = mock[CaptainDmo]
    val captainDmo2 = mock[CaptainDmo]
    val captainModel1 = mock[CaptainModel]
    val captainModel2 = mock[CaptainModel]
    val serializedModel1 = TestData.nextString
    val serializedModel2 = TestData.nextString
    val atoms1 = Set(TestData.atom, TestData.atom, TestData.atom)
    val atoms2 = Set(TestData.atom, TestData.atom)
    expecting {
      captainDmoRepository.findByPersonIds(Set(resourceRoot1.root.id, resourceRoot2.root.id)) andReturn List(captainDmo1, captainDmo2) once()
      captainDmo1.personId andReturn resourceRoot1.root.id once()
      captainDmo2.personId andReturn resourceRoot2.root.id once()
      captainModelFactory.build(captainDmo1) andReturn captainModel1 once()
      jsonMapper.serialize(captainModel1) andReturn serializedModel1 once()
      captainDmo1.atoms(resourceRoot1.root.origin) andReturn atoms1 once()
      captainModelFactory.build(captainDmo2) andReturn captainModel2 once()
      jsonMapper.serialize(captainModel2) andReturn serializedModel2 once()
      captainDmo2.atoms(resourceRoot2.root.origin) andReturn atoms2 once()
    }
    whenExecuting(captainDmoRepository, captainModelFactory, jsonMapper, captainDmo1, captainDmo2) {
      captainResourceBuilder.buildWithSession(resourceRoots)
    }
  }
}
