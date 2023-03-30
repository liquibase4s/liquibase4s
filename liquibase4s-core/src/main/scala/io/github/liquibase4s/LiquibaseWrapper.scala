package io.github.liquibase4s

import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import liquibase.{Contexts, LabelExpression}

import java.sql.{Connection, DriverManager}
import java.util.Date
import scala.jdk.CollectionConverters._

class LiquibaseWrapper(config: LiquibaseConfig) {

  def migrate(): Unit = {
    val connection = getConnection(config)
    val liquibase = createLiquibase(connection, config.changelog)
    val contexts = buildContexts(config.contexts)
    val labelExpression = buildLabelExpression(config.contexts)

    liquibase.validate()
    try liquibase.update(contexts, labelExpression)
    finally {
      liquibase.forceReleaseLocks()
      connection.rollback()
      connection.close()
    }
  }

  def validate(): Unit = {
    val connection = getConnection(config)
    val liquibase = createLiquibase(connection, config.changelog)

    liquibase.validate()
  }

  def rollbackToDate(dateToRollback: Date): Unit = {
    val connection = getConnection(config)
    val liquibase = createLiquibase(connection, config.changelog)
    val contexts = buildContexts(config.contexts)
    val labelExpression = buildLabelExpression(config.contexts)
    liquibase.rollback(dateToRollback, contexts, labelExpression)
  }

  private def createLiquibase(connection: Connection, changelog: String): liquibase.Liquibase = {
    val database = DatabaseFactory.getInstance.findCorrectDatabaseImplementation(
      new JdbcConnection(connection),
    )
    val classLoader = classOf[LiquibaseWrapper].getClassLoader
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

object LiquibaseWrapper {
  def apply(config: LiquibaseConfig): LiquibaseWrapper = new LiquibaseWrapper(config)
}
