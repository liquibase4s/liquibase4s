# liquibase4s - Liquibase Migrations For Scala

[![Scala Steward badge](https://img.shields.io/badge/Scala_Steward-helping-blue.svg?style=flat&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA4AAAAQCAMAAAARSr4IAAAAVFBMVEUAAACHjojlOy5NWlrKzcYRKjGFjIbp293YycuLa3pYY2LSqql4f3pCUFTgSjNodYRmcXUsPD/NTTbjRS+2jomhgnzNc223cGvZS0HaSD0XLjbaSjElhIr+AAAAAXRSTlMAQObYZgAAAHlJREFUCNdNyosOwyAIhWHAQS1Vt7a77/3fcxxdmv0xwmckutAR1nkm4ggbyEcg/wWmlGLDAA3oL50xi6fk5ffZ3E2E3QfZDCcCN2YtbEWZt+Drc6u6rlqv7Uk0LdKqqr5rk2UCRXOk0vmQKGfc94nOJyQjouF9H/wCc9gECEYfONoAAAAASUVORK5CYII=)](https://scala-steward.org)
[![Continuous Integration](https://github.com/liquibase4s/liquibase4s/workflows/Continuous%20Integration/badge.svg)](https://github.com/liquibase4s/liquibase4s/actions)
[![codecov](https://codecov.io/gh/liquibase4s/liquibase4s/branch/main/graph/badge.svg)](https://codecov.io/gh/liquibase4s/liquibase4s)
[![Latest version](https://img.shields.io/badge/liquibase4s-0.1.0-orange.svg)]((https://index.scala-lang.org/liquibase4s/liquibase4s))

liquibase4s is a simple, idiomatic wrapper library to run [Liquibase](https://www.liquibase.org) migrations
in Scala. We currently support `Identity`, [`scala.concurrent.Future`](https://docs.scala-lang.org/overviews/core/futures.html)
and [`cats.effect.IO`](https://typelevel.org/cats-effect/).

## Binaries

Builds are available for Scala 2.12 and 2.13.

```scala
libraryDependencies ++= Seq(
  "io.github.liquibase4s" %% "liquibase4s-core" % "0.1.0",
  
  // if you want to use cats.effect.IO
  "io.github.liquibase4s" %% "liquibase4s-cats-effect" % "0.1.0",
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