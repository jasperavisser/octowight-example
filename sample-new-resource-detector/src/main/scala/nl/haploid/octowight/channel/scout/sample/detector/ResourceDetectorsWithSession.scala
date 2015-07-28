package nl.haploid.octowight.channel.scout.sample.detector

import java.util

import nl.haploid.octowight.AtomChangeEvent
import nl.haploid.octowight.channel.scout.detector.ResourceDetector
import nl.haploid.octowight.registry.data.ResourceRoot
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import scalikejdbc.DB

import scala.collection.JavaConverters._

@Component
class ResourceDetectorsWithSession extends ResourceDetector {
  @Autowired private[this] val resourceDetectorsWithSession: util.List[ResourceDetectorWithSession] = null

  override lazy val atomCategories = {
    val detectors = resourceDetectorsWithSession.asScala.toSet
    detectors.flatMap(_.atomCategories)
  }

  override def detect(events: Traversable[AtomChangeEvent]): Traversable[ResourceRoot] = {
    DB localTx { implicit session =>
      resourceDetectorsWithSession.asScala.flatMap { detector =>
        detector.detect(events.filter(event => detector.atomCategories.contains(event.atomCategory)))
      }
    }
  }
}
