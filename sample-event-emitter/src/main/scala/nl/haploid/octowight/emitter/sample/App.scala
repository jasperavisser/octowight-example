package nl.haploid.octowight.emitter.sample

import nl.haploid.octowight.emitter.sample.repository.AtomChangeEventDmoRepository
import nl.haploid.octowight.emitter.service.EventChannelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.Scheduled
import scalikejdbc.DB

object App {
  def main(args: Array[String]): Unit = SpringApplication.run(classOf[App])
}

@EnableAutoConfiguration
@ComponentScan(basePackages = Array("nl.haploid.octowight"))
class App {
  @Autowired private[this] val eventChannelService: EventChannelService = null
  @Autowired private[this] val atomChangeEventDmoRepository: AtomChangeEventDmoRepository = null

  @Scheduled(fixedRate = 1000)
  def poll(): Unit = {
    DB localTx { implicit session =>
      val eventDmos = atomChangeEventDmoRepository.findAll
      val events = eventDmos.map(_.toAtomChangeEvent)
      eventChannelService.sendEvents(events)
      atomChangeEventDmoRepository.delete(eventDmos)
    }
  }
}
