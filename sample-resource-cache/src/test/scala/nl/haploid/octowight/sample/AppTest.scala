package nl.haploid.octowight.sample

import nl.haploid.octowight.registry.data.ResourceMessage
import nl.haploid.octowight.sample.data.{CaptainModel, JsonModelSerializer}
import nl.haploid.octowight.sample.service.{CaptainCacheService, ResourceConsumerService}
import nl.haploid.octowight.{Mocked, Tested}


class AppTest extends AbstractTest {

  @Tested private[this] val app: App = null

  @Mocked private[this] val resourceConsumerService: ResourceConsumerService = null
  @Mocked private[this] val cacheService: CaptainCacheService = null
  @Mocked private[this] val serializer: JsonModelSerializer[CaptainModel] = null

  behavior of "Resource cache application"

  it should "receive and store messages" in {
    val message1 = ResourceMessage(null, "One", true)
    val message2 = ResourceMessage(null, "Two", true)
    val messagesList = List(message1, message2)
    expecting {
      resourceConsumerService.consumeResourceMessages() andReturn messagesList once()
      messagesList.foreach(message => cacheService.saveResource(message) once())
      resourceConsumerService.commit() once()
    }
    whenExecuting(resourceConsumerService, cacheService, serializer) {
      app.poll()
    }
  }
}
