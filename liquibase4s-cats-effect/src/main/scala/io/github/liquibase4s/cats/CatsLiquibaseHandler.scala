package io.github.liquibase4s.cats

import cats.effect.{IO, Sync}
import io.github.liquibase4s.{LiquibaseConfig, LiquibaseMigration, MigrationHandler}

object CatsLiquibaseHandler {
  implicit def handler: MigrationHandler[IO] = liquibaseHandlerForCats[IO]

  implicit def liquibaseHandlerForCats[F[_]](implicit S: Sync[F]): MigrationHandler[F] =
    (config: LiquibaseConfig) => S.delay(LiquibaseMigration.migrate(config))
}
