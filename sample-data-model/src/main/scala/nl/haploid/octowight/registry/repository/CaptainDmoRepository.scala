package nl.haploid.octowight.registry.repository

import java.lang

import nl.haploid.octowight.model.sample.repository.CaptainDmo
import org.springframework.data.mongodb.repository.MongoRepository


trait CaptainDmoRepository extends MongoRepository[CaptainDmo, lang.Long]
