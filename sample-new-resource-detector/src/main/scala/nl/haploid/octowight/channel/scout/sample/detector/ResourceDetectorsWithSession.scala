package nl.haploid.octowight.channel.scout.sample.detector

import java.util

import nl.haploid.octowight.AtomGroup
import nl.haploid.octowight.channel.scout.detector.ResourceDetector
import nl.haploid.octowight.registry.data.ResourceRoot
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import scalikejdbc.DB

import scala.collection.JavaConverters._

@Component
class ResourceDetectorsWithSession extends ResourceDetector {
  @Autowired private[this] val _resourceDetectorsWithSession: util.List[ResourceDetectorWithSession] = null

  def resourceDetectorsWithSession = _resourceDetectorsWithSession.asScala.toSet

  override lazy val atomCategories = resourceDetectorsWithSession.flatMap(_.atomCategories)

  override def detect(atomGroup: AtomGroup, atomIds: Set[Long]): Set[ResourceRoot] = {
    DB localTx { implicit session =>
      resourceDetectorsWithSession
        .filter(_.atomCategories.contains(atomGroup.category))
        .flatMap(_.detect(atomGroup, atomIds))
    }
  }
}
