package io.github.liquibase4s

case class LiquibaseConfig(
    url: String,
    user: String,
    password: String,
    driver: String,
    changelog: String,
    contexts: Option[List[String]] = None,
    labels: Option[List[String]] = None,
    schemaName: Option[String] = None,
)
