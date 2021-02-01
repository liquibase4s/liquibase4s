package io.github.liquibase4s
import munit.FunSuite

import scala.concurrent.ExecutionContext.Implicits.global

class FutureLiquibaseHandlerSuite extends FunSuite {
  test("liquibase migration should bring future effect into scope by default") {
    Liquibase.migrate(TestConfig.liquibaseConfig)
  }
}
