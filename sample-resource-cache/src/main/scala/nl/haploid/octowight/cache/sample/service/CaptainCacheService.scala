package nl.haploid.octowight.cache.sample.service

import nl.haploid.octowight.model.sample.data.{CaptainModel, JsonModelSerializer}
import nl.haploid.octowight.model.sample.repository.{CaptainDmo, CaptainDmoRepository}
import nl.haploid.octowight.registry.data._
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CaptainCacheService {
  private[this] lazy val log = LoggerFactory.getLogger(getClass)

  @Autowired private[this] val serializer: JsonModelSerializer[CaptainModel] = null
  @Autowired private[this] val resourceDmoRepository: CaptainDmoRepository = null

  def saveResource(message: ResourceMessage): Unit = {
    val captainModel: CaptainModel = if (message.tombstone) null else serializer.deserialize(message.model, classOf[CaptainModel])
    val captainDmo: CaptainDmo = new CaptainDmo(id = message.resourceIdentifier.id, model = captainModel, tombstone = message.tombstone)
    resourceDmoRepository.save(captainDmo)
    log.debug(s"Saved resource ${message.model}")
  }
}
