# liquibase4s - Liquibase Migrations For Scala

liquibase4s is a simple, idiomatic wrapper library to run [Liquibase](https://www.liquibase.org) migrations
in Scala. We currently support `Identity`, [`scala.concurrent.Future`](https://docs.scala-lang.org/overviews/core/futures.html)
and [`cats.effect.IO`](https://typelevel.org/cats-effect/).

## Binaries

**Builds are not yet available!**

If you want to use liquibase4s, clone this repo and run `sbt +publishLocal`.

```scala
libraryDependencies ++= Seq(
  "io.github.liquibase4s" %% "liquibase4s-core" % "0.1.0-SNAPSHOT",
  
  // if you want to use cats.effect.IO
  "io.github.liquibase4s" %% "liquibase4s-cats-effect" % "0.1.0-SNAPSHOT",
)
```
## Getting Started

To run liquibase migrations you need to create a `LiquibaseConfig` and construct a `Liquibase` instance.
The default implementation uses `Identity` which means migrations will run synchronously.

```scala
import io.github.liquibase4s.{Liquibase, LiquibaseConfig}

val config: LiquibaseConfig = LiquibaseConfig(
  url = "jdbc:h2:mem:testdb",
  user = "test",
  password = "test",
  driver = "org.h2.Driver",
  changelog = "db/changelog/test.xml",
)

Liquibase(config).migrate() // returns Unit in case of success or throws Exception
```

Alternatively you can use `scala.concurrent.Future` to run migrations asynchronously.

```scala
import scala.concurrent.Future
import io.github.liquibase4s.FutureMigrationHandler._

import scala.concurrent.ExecutionContext.Implicits.global

Liquibase[Future](config).migrate() // returns Future[Unit]
```

`liquibase4s-cats-effect` provides support for `cats.effect.IO`.
```scala
import cats.effect.IO
import io.github.liquibase4s.cats.CatsMigrationHandler._

Liquibase[IO](config).migrate() // returns IO[Unit]
```

## Contributions
Contributions to liquibase4s are very welcome. 