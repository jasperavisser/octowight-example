package nl.haploid.octowight.builder.sample.builder


import nl.haploid.octowight.JsonMapper
import nl.haploid.octowight.builder.builder.ResourceBuilder
import nl.haploid.octowight.registry.data.{Atom, ResourceIdentifier, ResourceMessage, ResourceRoot}
import nl.haploid.octowight.source.sample.repository.CaptainDmoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import scalikejdbc.{DB, DBSession}

@Component
class CaptainResourceBuilder extends ResourceBuilder {
  @Autowired private[this] val captainDmoRepository: CaptainDmoRepository = null
  @Autowired private[this] val captainModelFactory: CaptainModelFactory = null
  @Autowired private[this] val jsonMapper: JsonMapper = null

  override val collection = "captain"

  override def build(resourceRoots: Iterable[ResourceRoot]): Iterable[(ResourceMessage, Set[Atom])] = {
    DB localTx { implicit session => buildWithSession(resourceRoots) }
  }

  def buildWithSession(resourceRoots: Iterable[ResourceRoot])(implicit session: DBSession): Iterable[(ResourceMessage, Set[Atom])] = {
    val personIds: Set[Long] = resourceRoots.map(_.root.id).toSet
    val captainDmos = captainDmoRepository.findByPersonIds(personIds)
    val captainDmosById = captainDmos.groupBy(_.personId)
    resourceRoots.map { resourceRoot =>
      val resourceIdentifier = new ResourceIdentifier(collection = resourceRoot.resourceCollection, id = resourceRoot.resourceId)
      // TODO: there really shouldn't be multiple
      captainDmosById.get(resourceRoot.root.id) match {
        case Some(captainDmosForResourceRoot) =>
          val captainDmo = captainDmosForResourceRoot.head
          val model = captainModelFactory.build(captainDmo)
          val resource = new ResourceMessage(resourceIdentifier = resourceIdentifier, model = jsonMapper.serialize(model), tombstone = false)
          (resource, captainDmo.atoms(resourceRoot.root.origin))
        case None =>
          val resource = new ResourceMessage(resourceIdentifier = resourceIdentifier, model = "", tombstone = true)
          (resource, Set[Atom]())
      }
    }
  }


}
