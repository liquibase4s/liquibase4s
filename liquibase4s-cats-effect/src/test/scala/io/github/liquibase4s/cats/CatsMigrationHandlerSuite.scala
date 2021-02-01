package io.github.liquibase4s.cats

import cats.effect.IO
import io.github.liquibase4s.{Liquibase, TestConfig}
import liquibase.exception.ChangeLogParseException
import munit.CatsEffectSuite

class CatsMigrationHandlerSuite extends CatsEffectSuite {
  import CatsMigrationHandler._

  test("liquibase migration should use cats IO") {
    Liquibase[IO](TestConfig.liquibaseConfig).migrate().assertEquals(())
  }

  test("liquibase validation should use cats IO") {
    Liquibase[IO](TestConfig.liquibaseConfig).validate().assertEquals(())
  }

  test("liquibase validation should raise an error if changelog is invalid") {
    val result = Liquibase[IO](TestConfig.liquibaseConfigInvalidChangelog)
      .validate()
      .attempt

    result.map(_.isLeft).assert
    result.map(_.swap.getOrElse(())).map(_.isInstanceOf[ChangeLogParseException]).assert
  }
}
