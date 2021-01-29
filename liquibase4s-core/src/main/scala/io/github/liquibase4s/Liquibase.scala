package io.github.liquibase4s

import scala.concurrent.{ExecutionContext, Future}

object Liquibase {
  def migrate[F[_]](config: LiquibaseConfig)(implicit handler: MigrationHandler[F]): F[Unit] =
    handler.migrate(config)
}
