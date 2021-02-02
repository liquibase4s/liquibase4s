package io.github.liquibase4s.cats

import cats.effect.{IO, Sync}
import io.github.liquibase4s.{LiquibaseConfig, LiquibaseWrapper, MigrationHandler}

object CatsMigrationHandler {

  implicit def handler: MigrationHandler[IO] = liquibaseHandlerForCats[IO]

  implicit def liquibaseHandlerForCats[F[_]](implicit S: Sync[F]): MigrationHandler[F] = new MigrationHandler[F] {

    override def migrate(config: LiquibaseConfig): F[Unit] = S.delay(LiquibaseWrapper(config).migrate())

    override def validate(config: LiquibaseConfig): F[Unit] = S.delay(LiquibaseWrapper(config).validate())
  }
}
