package io.github.liquibase4s
import munit.FunSuite

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class FutureLiquibaseHandlerSpec extends FunSuite {
  test("liquibase migration should bring future effect into scope by default") {
    Liquibase.migrate[Future](TestConfig.liquibaseConfig)
  }
}
