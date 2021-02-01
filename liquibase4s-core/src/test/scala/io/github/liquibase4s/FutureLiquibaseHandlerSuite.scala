package io.github.liquibase4s
import liquibase.exception.{ChangeLogParseException, LiquibaseParseException}
import munit.FunSuite

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Failure

class FutureLiquibaseHandlerSuite extends FunSuite {
  test("liquibase migration should bring future effect into scope") {
    Liquibase(TestConfig.liquibaseConfig).migrate().map(assertEquals(_, ()))
  }

  test("liquibase validation should bring future effect into scope") {
    Liquibase(TestConfig.liquibaseConfig).validate().map(assertEquals(_, ()))
  }

  test("liquibase validation should raise an error if changelog is invalid") {
    Liquibase(TestConfig.liquibaseConfigInvalidChangelog)
      .validate()
      .onComplete(result => assert(result.isInstanceOf[Failure[ChangeLogParseException]]))
  }
}
