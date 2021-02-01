package io.github.liquibase4s

object TestConfig {

  val liquibaseConfig: LiquibaseConfig = LiquibaseConfig(
    url = "jdbc:h2:mem:testdb",
    user = "test",
    password = "test",
    driver = "org.h2.Driver",
    changelog = "db/changelog/test.xml",
  )

  val liquibaseConfigInvalidChangelog: LiquibaseConfig = liquibaseConfig.copy(
    changelog = "db/changelog/invalid.xml",
  )
}
