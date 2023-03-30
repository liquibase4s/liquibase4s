package io.github.liquibase4s

import java.util.Date
import scala.concurrent.{ExecutionContext, Future}

object FutureMigrationHandler {

  implicit def handler(implicit ec: ExecutionContext): MigrationHandler[Future] = new MigrationHandler[Future] {

    override def migrate(config: LiquibaseConfig): Future[Unit] =
      Future(LiquibaseWrapper(config).migrate())

    override def validate(config: LiquibaseConfig): Future[Unit] =
      Future(LiquibaseWrapper(config).validate())

    override def rollbackToDate(config: LiquibaseConfig, dateToRollback: Date): Future[Unit] =
      Future(LiquibaseWrapper(config).rollbackToDate(dateToRollback))
  }
}
