package io.github.liquibase4s

class Liquibase[F[_]](config: LiquibaseConfig)(implicit handler: MigrationHandler[F]) {

  /** Runs liquibase migrations.
    */
  def migrate(): F[Unit] = handler.migrate(config)

  /** Checks and identifies any possible errors in a changelog that can cause
    * migrations to fail.
    */
  def validate(): F[Unit] = handler.validate(config)
}

object Liquibase {
  def apply[F[_]](config: LiquibaseConfig)(implicit handler: MigrationHandler[F]): Liquibase[F] = new Liquibase(config)
}
