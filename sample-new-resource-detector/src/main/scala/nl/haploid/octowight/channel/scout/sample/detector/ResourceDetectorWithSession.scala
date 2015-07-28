package nl.haploid.octowight.channel.scout.sample.detector

import nl.haploid.octowight.AtomChangeEvent
import nl.haploid.octowight.registry.data.ResourceRoot
import scalikejdbc.DBSession

trait ResourceDetectorWithSession {

  def atomCategories: Set[String]

  def detect(events: Traversable[AtomChangeEvent])(implicit session: DBSession): Traversable[ResourceRoot]
}
