package io.github.liquibase4s.cats

import cats.effect.{IO, Sync}
import io.github.liquibase4s.{LiquibaseConfig, MigrationHandler, SchemaMigration}

object CatsMigrationHandler {

  implicit def handler: MigrationHandler[IO] = liquibaseHandlerForCats[IO]

  implicit def liquibaseHandlerForCats[F[_]](implicit S: Sync[F]): MigrationHandler[F] = new MigrationHandler[F] {

    override def migrate(config: LiquibaseConfig): F[Unit] = S.delay(SchemaMigration(config).migrate())

    override def validate(config: LiquibaseConfig): F[Unit] = S.delay(SchemaMigration(config).validate())
  }
}
