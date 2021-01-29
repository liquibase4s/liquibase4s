package io.github.liquibase4s

import liquibase.{Contexts, LabelExpression, Liquibase}
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor

import java.sql.DriverManager

object LiquibaseMigration {

  def migrate(config: LiquibaseConfig): Unit = {
    val connection = DriverManager.getConnection(config.url, config.user, config.password)
    val database = DatabaseFactory.getInstance.findCorrectDatabaseImplementation(
      new JdbcConnection(connection),
    )
    val liquibase =
      new Liquibase(config.changelog, new ClassLoaderResourceAccessor(), database)

    liquibase.update(new Contexts(), new LabelExpression())
  }
}
