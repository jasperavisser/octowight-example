package nl.haploid.octowight.emitter.sample.repository

import nl.haploid.octowight.AtomChangeEvent
import scalikejdbc.WrappedResultSet

object AtomChangeEventDmo {

  def apply(rs: WrappedResultSet): AtomChangeEventDmo =
    new AtomChangeEventDmo(id = rs.get("id"), atomId = rs.get("atom_id"), atomOrigin = rs.get("atom_origin"), atomCategory = rs.get("atom_category"))
}

case class AtomChangeEventDmo(id: Long, atomId: Long, atomOrigin: String, atomCategory: String) {

  def toAtomChangeEvent =
    new AtomChangeEvent(id = id, atomId = atomId, atomOrigin = atomOrigin, atomCategory = atomCategory)
}
