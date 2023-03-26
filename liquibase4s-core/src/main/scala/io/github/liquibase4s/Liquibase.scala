package io.github.liquibase4s

import java.util.Date

class Liquibase[F[_]](config: LiquibaseConfig)(implicit handler: MigrationHandler[F]) {

  /** Runs liquibase migrations.
    */
  def migrate(): F[Unit] = handler.migrate(config)

  /** Checks and identifies any possible errors in a changelog that can cause migrations to fail.
    */
  def validate(): F[Unit] = handler.validate(config)

  def rollbackToDate(dateToRollback: Date): F[Unit] = handler.rollbackToDate(config, dateToRollback)
}

object Liquibase {
  def apply[F[_]](config: LiquibaseConfig)(implicit handler: MigrationHandler[F]): Liquibase[F] = new Liquibase(config)
}
