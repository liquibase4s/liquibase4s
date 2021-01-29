package io.github.liquibase4s

case class LiquibaseConfig(
    url: String,
    user: String,
    password: String,
    changelog: String,
)
