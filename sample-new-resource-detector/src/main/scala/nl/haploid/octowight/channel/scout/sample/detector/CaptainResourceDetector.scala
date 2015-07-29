package nl.haploid.octowight.channel.scout.sample.detector

import nl.haploid.octowight.AtomGroup
import nl.haploid.octowight.registry.data.{Atom, ResourceRoot}
import nl.haploid.octowight.source.sample.data.CaptainResource
import nl.haploid.octowight.source.sample.repository.{CaptainDmo, CaptainDmoRepository}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import scalikejdbc.DBSession

object CaptainResourceDetector {
  val RoleType = "captain"
}

@Component
class CaptainResourceDetector extends ResourceDetectorWithSession {
  @Autowired protected val captainDmoRepository: CaptainDmoRepository = null

  override def atomCategories = Set("person")

  override def detect(atomGroup: AtomGroup, atomIds: Set[Long])(implicit session: DBSession): Set[ResourceRoot] = {
    val captainsByPersonId = findCaptainsByPersonId(atomIds)
    atomIds
      .filter(atomId => captainsByPersonId.get(atomId).isDefined)
      .map(atomId => ResourceRoot(root = Atom(id = atomId, group = atomGroup), resourceCollection = CaptainResource.ResourceCollection))
  }

  def findCaptainsByPersonId(personIds: Set[Long])(implicit session: DBSession): Map[Long, CaptainDmo] = {
    captainDmoRepository.findByPersonIds(personIds)
      .map(captainDmo => captainDmo.personId -> captainDmo)
      .toMap
  }
}
