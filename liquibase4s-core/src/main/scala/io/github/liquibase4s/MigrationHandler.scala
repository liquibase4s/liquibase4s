package io.github.liquibase4s

import scala.concurrent.{ExecutionContext, Future}

trait MigrationHandler[F[_]] {
  def migrate(config: LiquibaseConfig): F[Unit]
}

object MigrationHandler {
  def apply[F[_]: MigrationHandler]: MigrationHandler[F] = implicitly[MigrationHandler[F]]

  implicit def defaultHandler(implicit ec: ExecutionContext): MigrationHandler[Future] = FutureMigrationHandler.handler
}
