package nl.haploid.octowight.cache.sample

import nl.haploid.octowight.cache.sample.service.{CaptainCacheService, ResourceConsumerService}
import nl.haploid.octowight.model.sample.data.{CaptainModel, JsonModelSerializer}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.Scheduled

object App {
  def main(args: Array[String]): Unit = SpringApplication.run(classOf[App])
}

@EnableAutoConfiguration
@ComponentScan(basePackages = Array("nl.haploid.octowight"))
class App {
  @Autowired private[this] val resourceConsumerService: ResourceConsumerService = null
  @Autowired private[this] val captainCacheService: CaptainCacheService = null
  @Autowired private[this] val captainModelSerializer: JsonModelSerializer[CaptainModel] = null

  @Scheduled(fixedRate = 1000)
  def poll(): Unit = {
    val resourceMessages = resourceConsumerService.consumeResourceMessages()
    resourceMessages.foreach(captainCacheService.saveResource)
    resourceConsumerService.commit()
  }
}
