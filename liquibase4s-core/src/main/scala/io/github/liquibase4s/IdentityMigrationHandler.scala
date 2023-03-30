package io.github.liquibase4s

import java.util.Date

object IdentityMigrationHandler {

  type Identity[A] = A

  implicit def handler: MigrationHandler[Identity] = new MigrationHandler[Identity] {

    override def migrate(config: LiquibaseConfig): Unit =
      LiquibaseWrapper(config).migrate()

    override def validate(config: LiquibaseConfig): Unit =
      LiquibaseWrapper(config).validate()

    override def rollbackToDate(config: LiquibaseConfig, dateToRollback: Date): Unit =
      LiquibaseWrapper(config).rollbackToDate(dateToRollback)
  }
}
