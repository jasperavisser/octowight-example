package nl.haploid.octowight.channel.scout.sample.detector

import nl.haploid.octowight.AtomChangeEvent
import nl.haploid.octowight.registry.data.ResourceRoot
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

  override def detect(events: Traversable[AtomChangeEvent])(implicit session: DBSession): Set[ResourceRoot] = {
    val captainsByPersonId = findCaptainsByPersonId(events)
    events
      .filter(event => captainsByPersonId.get(event.atomId).isDefined)
      .map(ResourceRoot(_, CaptainResource.ResourceCollection))
      .toSet
  }

  def findCaptainsByPersonId(events: Traversable[AtomChangeEvent])(implicit session: DBSession): Map[Long, CaptainDmo] = {
    val personIds = events.map(_.atomId).toSet
    captainDmoRepository.findByPersonIds(personIds)
      .map(captainDmo => captainDmo.personId -> captainDmo)
      .toMap
  }
}
