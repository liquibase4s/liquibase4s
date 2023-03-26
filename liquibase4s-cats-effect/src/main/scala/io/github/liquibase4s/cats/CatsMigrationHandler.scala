package io.github.liquibase4s.cats

import cats.effect.Sync
import io.github.liquibase4s.{LiquibaseConfig, LiquibaseWrapper, MigrationHandler}

import java.util.Date

object CatsMigrationHandler {

  implicit def liquibaseHandlerForCats[F[_]: Sync]: MigrationHandler[F] = new MigrationHandler[F] {

    override def migrate(config: LiquibaseConfig): F[Unit] =
      Sync[F].delay(LiquibaseWrapper(config).migrate())

    override def validate(config: LiquibaseConfig): F[Unit] =
      Sync[F].delay(LiquibaseWrapper(config).validate())

    override def rollbackToDate(config: LiquibaseConfig, rollbackTo: Date): F[Unit] =
      Sync[F].delay(LiquibaseWrapper(config).rollbackToDate(rollbackTo))
  }
}
