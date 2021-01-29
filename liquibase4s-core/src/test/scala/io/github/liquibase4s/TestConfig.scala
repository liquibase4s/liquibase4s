package io.github.liquibase4s

object TestConfig {
  val liquibaseConfig: LiquibaseConfig = LiquibaseConfig(
    url = "jdbc:h2:mem:testdb",
    user = "test",
    password = "test",
    changelog = "db/changelog/test.yml",
  )
}
