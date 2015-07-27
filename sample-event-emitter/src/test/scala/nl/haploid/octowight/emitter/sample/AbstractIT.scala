package nl.haploid.octowight.emitter.sample

import nl.haploid.octowight.emitter.configuration.KafkaConfiguration
import nl.haploid.octowight.emitter.sample.configuration.{PostgresConfiguration, TestConfiguration}
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner
import org.scalatest.tags.Slow
import org.springframework.test.context.support.AnnotationConfigContextLoader
import org.springframework.test.context.{ContextConfiguration, TestContextManager}
import scalikejdbc.scalatest.AutoRollback

@RunWith(classOf[JUnitRunner])
@ContextConfiguration(classes = Array(classOf[TestConfiguration], classOf[KafkaConfiguration], classOf[PostgresConfiguration]), loader = classOf[AnnotationConfigContextLoader])
@Slow
abstract class AbstractIT extends fixture.FlatSpec with BeforeAndAfterEach with ShouldMatchers with AutoRollback {

  override def beforeEach() = new TestContextManager(this.getClass).prepareTestInstance(this)
}
