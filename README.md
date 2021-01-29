# liquibase4s - Liquibase Migrations For Scala

liquibase4s provides a simple, idiomatic API to run [Liquibase](https://www.liquibase.org) migrations
in Scala. We currently support [scala.concurrent.Future](https://docs.scala-lang.org/overviews/core/futures.html)
and [cats.effect.IO](https://typelevel.org/cats-effect/).

## Binaries

Builds are available for Scala 2.12 and 2.13

```scala
libraryDependencies ++= Seq(
  "io.github.liquibase4s" %% "liquibase4s-core" % "0.1.0",
  
  // if you want to use cats.effect.IO
  "io.github.liquibase4s" %% "liquibase4s-cats-effect" % "0.1.0",
)
```
## Getting Started

To run liquibase migrations you need to create a `LiquibaseConfig` and pass it to `Liquibase.migrate()`.
The default implementation uses `scala.concurrent.Future`.

```scala
import io.github.liquibase4s.{Liquibase, LiquibaseConfig}

val config: LiquibaseConfig = LiquibaseConfig(
  url = "jdbc:h2:mem:testdb",
  user = "test",
  password = "test",
  changelog = "db/changelog/test.xml",
)

Liquibase.migrate(config).map(_ => //...)
```

Alternatively you can use `cats.effect.IO`.
```scala
import io.github.liquibase4s.cats.CatsLiquibaseHandler._

Liquibase.migrate[IO](config)
```

## Contributions
Contributions to liquibase4s are very welcome. 