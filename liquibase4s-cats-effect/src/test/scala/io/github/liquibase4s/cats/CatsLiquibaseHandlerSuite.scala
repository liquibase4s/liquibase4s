package io.github.liquibase4s.cats

import cats.effect.IO
import io.github.liquibase4s.{Liquibase, TestConfig}
import munit.CatsEffectSuite

class CatsLiquibaseHandlerSuite extends CatsEffectSuite {

  test("liquibase migration should use cats IO") {
    import CatsLiquibaseHandler._
    Liquibase.migrate[IO](TestConfig.liquibaseConfig)
  }
}
