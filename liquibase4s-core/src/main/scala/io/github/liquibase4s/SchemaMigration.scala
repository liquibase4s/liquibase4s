package io.github.liquibase4s

import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import liquibase.{Contexts, LabelExpression}

import java.sql.{Connection, DriverManager}
import scala.jdk.CollectionConverters._

class SchemaMigration {

  def migrate(config: LiquibaseConfig): Unit = {
    val connection = getConnection(config)
    val liquibase = createLiquibase(connection, config.changelog)
    val contexts = buildContexts(config.contexts)
    val labelExpression = buildLabelExpression(config.contexts)

    try liquibase.update(contexts, labelExpression)
    finally {
      liquibase.forceReleaseLocks()
      connection.rollback()
      connection.close()
    }
  }

  private def createLiquibase(connection: Connection, changelog: String): liquibase.Liquibase = {
    val database = DatabaseFactory.getInstance.findCorrectDatabaseImplementation(
      new JdbcConnection(connection),
    )
    val classLoader = classOf[SchemaMigration].getClassLoader
    val resourceAccessor = new ClassLoaderResourceAccessor(classLoader)
    new liquibase.Liquibase(changelog, resourceAccessor, database)
  }

  private def getConnection(config: LiquibaseConfig): Connection = {
    Class.forName(config.driver)
    DriverManager.getConnection(config.url, config.user, config.password)
  }

  private def buildContexts(contexts: Option[List[String]]): Contexts =
    contexts
      .map { ctx =>
        new Contexts(ctx.asJava)
      }
      .getOrElse(new Contexts())

  private def buildLabelExpression(labels: Option[List[String]]): LabelExpression =
    labels
      .map { l =>
        new LabelExpression(l.asJava)
      }
      .getOrElse(new LabelExpression())
}

object SchemaMigration extends SchemaMigration
