package io.github.liquibase4s

class Liquibase[F[_]](config: LiquibaseConfig)(implicit handler: MigrationHandler[F]) {
  def migrate(): F[Unit] = handler.migrate(config)

  def validate(): F[Unit] = handler.validate(config)
}

object Liquibase {
  def apply[F[_]](config: LiquibaseConfig)(implicit handler: MigrationHandler[F]): Liquibase[F] = new Liquibase(config)
}
