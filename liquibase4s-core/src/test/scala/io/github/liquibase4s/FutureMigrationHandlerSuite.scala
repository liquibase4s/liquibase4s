package io.github.liquibase4s
import liquibase.exception.ChangeLogParseException
import munit.FunSuite

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class FutureMigrationHandlerSuite extends FunSuite {
  import FutureMigrationHandler._

  test("liquibase migration should bring future effect into scope") {
    Liquibase[Future](TestConfig.liquibaseConfig).migrate().map(assertEquals(_, ()))
  }

  test("liquibase validation should bring future effect into scope") {
    Liquibase[Future](TestConfig.liquibaseConfig).validate().map(assertEquals(_, ()))
  }

  test("liquibase validation should raise an error if changelog is invalid") {
    Liquibase[Future](TestConfig.liquibaseConfigInvalidChangelog)
      .validate()
      .failed
      .map(ex => assert(ex.isInstanceOf[ChangeLogParseException]))
  }
}
