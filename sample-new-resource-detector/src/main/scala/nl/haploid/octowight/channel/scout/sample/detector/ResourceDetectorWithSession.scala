package nl.haploid.octowight.channel.scout.sample.detector

import nl.haploid.octowight.AtomGroup
import nl.haploid.octowight.registry.data.ResourceRoot
import scalikejdbc.DBSession

trait ResourceDetectorWithSession {

  def atomCategories: Set[String]

  def detect(atomGroup: AtomGroup, atomIds: Set[Long])(implicit session: DBSession): Set[ResourceRoot]
}
