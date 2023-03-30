package io.github.liquibase4s

import liquibase.exception.ChangeLogParseException
import munit.FunSuite

import java.util.Date

class IdentityMigrationHandlerSuite extends FunSuite {

  test("liquibase migration should bring identity effect into scope") {
    assertEquals(Liquibase(TestConfig.liquibaseConfig).migrate(), ())
  }

  test("liquibase validation should bring identity effect into scope") {
    assertEquals(Liquibase(TestConfig.liquibaseConfig).validate(), ())
  }

  test("liquibase rollback to date should bring identity effect into scope") {
    assertEquals(Liquibase(TestConfig.liquibaseConfig).rollbackToDate(new Date()), ())
  }

  test("liquibase validation should raise an error if changelog is invalid") {
    intercept[ChangeLogParseException](Liquibase(TestConfig.liquibaseConfigInvalidChangelog).validate())
  }
}
