package services

import models.sql.ApiDao
import sql.{ApiTable, SqlCompanion}
import java.util.UUID
import java.sql.JDBCType

import scala.concurrent.{ExecutionContext, Future}
import sql.CustomPostgresProfile.api._
import slick.jdbc.{PositionedParameters, SetParameter, GetResult}
import slick.dbio.DBIO

object DatabaseService {
  implicit val uuidSetParameter = new SetParameter[UUID] {
    def apply(v: UUID, pp: PositionedParameters) = pp.setObject(v.toString, JDBCType.OTHER.getVendorTypeNumber())
  }

  implicit val uuidOptionSetParameter = new SetParameter[Option[UUID]] {
    def apply(v: Option[UUID], pp: PositionedParameters) = pp.setObjectOption(v.map(_.toString), JDBCType.OTHER.getVendorTypeNumber())
  }

  implicit val uuidResult = GetResult(r => r.nextObject.asInstanceOf[UUID])

  def literalOrNull(idOpt: Option[UUID]): String = {
    idOpt.map(id => s"'${id}'").getOrElse("NULL")
  }
}

class DatabaseService[T <: ApiDao, U <: ApiTable[T]](companion: SqlCompanion[T, U], database: Database) {

  implicit val db = database
  val tquery = companion.tquery
  val tqueryWithId = companion.tqueryWithId

  def list(skip: Int = 0, limit: Int = 50): Future[Seq[T]] = {
    val query = for (m <- tquery) yield (m)
    db.run(query.drop(skip).take(limit).result)
  }

  def insert(model: T): Future[UUID] = {
    db.run(tqueryWithId += model)
  }

  def insertAll(models: Seq[T]): Future[Option[Int]] = {
    db.run(tquery ++= models)
  }

  def findById(id: UUID): Future[Option[T]] = {
    db.run(findByIdQuery(id).result.headOption)
  }

  def delete(id: UUID)(implicit ec: ExecutionContext): Future[Boolean] = {
    db.run(tquery.filter(_.id === id).delete).map(_ > 0)
  }

  def deleteAll(ids: Seq[UUID])(implicit ec: ExecutionContext): Future[Boolean] = {
    db.run(tquery.filter(_.id.inSet(ids)).delete).map(_ > 0)
  }

  def update(id: UUID, model: T)(implicit ec: ExecutionContext): Future[Boolean] = {
    val query = for { ms <- tquery if ms.id === id } yield ms
    db.run(query.update(model)).map(_ > 0)
  }

  def updateAll(models: Seq[T])(implicit ec: ExecutionContext): Future[Boolean] = {
    val updates = models.map(m => updateQuery(m.id, m))
    db.run(DBIO.sequence(updates)).map(_ => true)
  }

  val findByIdQuery = Compiled { (id: Rep[UUID]) =>
    tquery.filter(_.id === id).take(1)
  }

  def updateQuery(id: UUID, model: T) = {
    val query = for { ms <- tquery if ms.id === id } yield ms
    query.update(model)
  }
}
