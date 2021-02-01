package io.github.liquibase4s

import scala.concurrent.{ExecutionContext, Future}

object FutureMigrationHandler {
  implicit def handler(implicit ec: ExecutionContext): MigrationHandler[Future] = { (config: LiquibaseConfig) =>
    Future(SchemaMigration.migrate(config))
  }
}
