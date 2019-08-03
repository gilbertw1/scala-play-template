package models.sql

import java.util.UUID

import slick.jdbc.PositionedResult

abstract class ApiDao {
  def id: UUID
}

object DaoFieldExtractor {
  def apply[T](col: String)(f: (PositionedResult) => T): DaoFieldExtractor[T] = {
    new DaoFieldExtractor[T](col, f)
  }
}

class DaoFieldExtractor[T](val col: String, val f: (PositionedResult) => T) {
  def apply(r: PositionedResult): T = {
    f(r)
  }
}
