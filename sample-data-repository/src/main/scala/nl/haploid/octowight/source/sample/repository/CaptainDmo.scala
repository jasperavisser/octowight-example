package nl.haploid.octowight.source.sample.repository

import nl.haploid.octowight.registry.data.Atom

case class CaptainDmo(personId: Long, name: String, roleIds: Set[Long]) {

  def atoms(origin: String) =
    Set(Atom(id = personId, category = "person", origin = origin)) ++ roleIds.map(roleId => Atom(id = roleId, category = "role", origin = origin))
}

