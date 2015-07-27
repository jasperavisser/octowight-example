package nl.haploid.octowight.cache.sample.configuration

import nl.haploid.octowight.JsonMapper
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableScheduling
@EnableMongoRepositories(basePackages = Array("nl.haploid.octowight.cache.sample.repository"))
class AppConfiguration {

  @Bean def jsonMapper = new JsonMapper
}