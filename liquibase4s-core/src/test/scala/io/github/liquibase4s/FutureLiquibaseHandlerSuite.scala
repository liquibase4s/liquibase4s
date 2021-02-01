package io.github.liquibase4s
import liquibase.exception.ChangeLogParseException
import munit.FunSuite

import scala.concurrent.ExecutionContext.Implicits.global

class FutureLiquibaseHandlerSuite extends FunSuite {
  test("liquibase migration should bring future effect into scope") {
    Liquibase(TestConfig.liquibaseConfig).migrate.map(assertEquals(_, ()))
  }

  test("liquibase validation should bring future effect into scope") {
    Liquibase(TestConfig.liquibaseConfig).validate.map(assertEquals(_, ()))
  }

  test("liquibase validation should raise an error if changelog is invalid") {
    Liquibase(TestConfig.liquibaseConfigInvalidChangelog).validate.failed.map(_.asInstanceOf[ChangeLogParseException])
  }
}
