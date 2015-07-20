package nl.haploid.octowight.sample.repository

import org.springframework.beans.factory.annotation.Autowired
import scalikejdbc.{DataSourceConnectionPool, _}

trait ScalikeJdbcAccess {
  @Autowired protected val connectionPool: DataSourceConnectionPool = null

  def setOfLongs(longs: Traversable[Long]): SQLSyntax = SQLSyntax.createUnsafely(longs.mkString(", "))

  def setPercept(schema: String)(implicit session: DBSession) = {
    sql"select set_percept($schema)".execute().apply()
  }
}
