package io.github.liquibase4s

object IdentityMigrationHandler {

  type Identity[A] = A

  implicit def handler: MigrationHandler[Identity] = new MigrationHandler[Identity] {

    override def migrate(config: LiquibaseConfig): Unit = SchemaMigration(config).migrate()

    override def validate(config: LiquibaseConfig): Unit = SchemaMigration(config).validate()
  }
}
