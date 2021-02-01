package io.github.liquibase4s.cats

import cats.effect.{IO, Sync}
import io.github.liquibase4s.{LiquibaseConfig, MigrationHandler, SchemaMigration}

object CatsLiquibaseHandler {
  implicit def handler: MigrationHandler[IO] = liquibaseHandlerForCats[IO]

  implicit def liquibaseHandlerForCats[F[_]](implicit S: Sync[F]): MigrationHandler[F] =
    (config: LiquibaseConfig) => S.delay(SchemaMigration.migrate(config))
}
