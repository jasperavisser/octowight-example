package nl.haploid.octowight.emitter.sample.repository

import org.springframework.stereotype.Repository
import scalikejdbc._

@Repository
class AtomChangeEventDmoRepository {

  def delete(atomChangeEventDmos: List[AtomChangeEventDmo])(implicit session: DBSession): Int = {
    if (atomChangeEventDmos.nonEmpty) {
      val ids = atomChangeEventDmos.map(_.id).toSet
      sql"delete from octowight.atom_change_event where id in ($ids)".update().apply()
    } else {
      0
    }
  }

  def findAll()(implicit session: DBSession): List[AtomChangeEventDmo] = {
    sql"""
      select id, atom_id, atom_origin, atom_category from octowight.atom_change_event
    """.map(AtomChangeEventDmo(_)).list().apply()
  }

  private[repository] def deleteAll()(implicit session: DBSession): Int =
    sql"delete from octowight.atom_change_event".update().apply()

  private[repository] def insert(atomChangeEventDmo: AtomChangeEventDmo)(implicit session: DBSession): AtomChangeEventDmo = {
    val id = sql"""
      insert into octowight.atom_change_event(atom_id, atom_origin, atom_category)
      values(${atomChangeEventDmo.atomId}, ${atomChangeEventDmo.atomOrigin}, ${atomChangeEventDmo.atomCategory})
    """.updateAndReturnGeneratedKey("id").apply()
    atomChangeEventDmo.copy(id = id)
  }
}
