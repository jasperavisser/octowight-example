package nl.haploid.octowight.source.sample.repository

import org.springframework.stereotype.Repository
import scalikejdbc._

@Repository
class CaptainDmoRepository {

  def toPersonDmoAndRoleId(rs: WrappedResultSet): (PersonDmo, Long) =
    (PersonDmo(id = rs.long("person_id"), name = rs.string("name")), rs.long("role_id"))

  def findByPersonIds(personIds: Set[Long])(implicit session: DBSession): List[CaptainDmo] = {
    val personDmosAndRoleIds = sql"""
      select
        person.id as person_id,
        person.name,
        role.id as role_id
      from octowight.person
      inner join octowight.role
        on role.person = person.id
        and role.name = 'captain'
    """.map(toPersonDmoAndRoleId).list().apply()
    personDmosAndRoleIds
      .groupBy(_._1)
      .mapValues(_.map(_._2))
      .toList
      .map { case (personDmo, roleIds) => CaptainDmo(personId = personDmo.id, name = personDmo.name, roleIds = roleIds.toSet) }
  }
}
