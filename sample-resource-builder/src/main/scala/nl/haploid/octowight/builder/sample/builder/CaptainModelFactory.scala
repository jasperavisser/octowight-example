package nl.haploid.octowight.builder.sample.builder

import nl.haploid.octowight.model.sample.data.CaptainModel
import nl.haploid.octowight.source.sample.repository.CaptainDmo
import org.springframework.stereotype.Component

@Component
class CaptainModelFactory {

  def build(captainDmo: CaptainDmo) =
    CaptainModel(id = captainDmo.personId, name = captainDmo.name)
}
