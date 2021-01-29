val CatsVersion = "2.3.1"
val LiquibaseVersion = "4.2.2"
val SnakeYamlVersion = "1.27"

val MunitVersion = "0.7.21"
val MunitCatsEffectVersion = "0.13.0"
val H2Version = "1.4.192"


lazy val commonSettings = Seq(
  organization := "io.github.liquibase4s",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.13.4",
  crossScalaVersions := Seq("2.12.11", "2.13.4")
)

lazy val root = project
  .in(file("."))
  .settings(name := "liquibase4s")
  .settings(commonSettings)
  .aggregate(
    core,
    catsEffect
  )

lazy val core = project
  .in(file("liquibase4s-core"))
  .settings(commonSettings)
  .settings(
    name := "liquibase4s-core",
    libraryDependencies ++= Seq(
      "org.liquibase" % "liquibase-core" % LiquibaseVersion,
      "org.yaml" % "snakeyaml" % SnakeYamlVersion,        // Required by liquibase YAML migrations

      "org.scalameta" %% "munit" % MunitVersion % Test,
      "com.h2database" % "h2" % H2Version % Test
    )
  )

lazy val catsEffect = project
  .in(file("liquibase4s-cats-effect"))
  .dependsOn(core)
  .settings(commonSettings)
  .settings(
    name := "liquibase4s-cats-effect",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % CatsVersion,
      "org.typelevel" %% "cats-effect" % CatsVersion,

      "org.scalameta" %% "munit" % MunitVersion % Test,
      "org.typelevel" %% "munit-cats-effect-2" % MunitCatsEffectVersion % Test,
      "com.h2database" % "h2" % H2Version % Test
    )
  )

scalacOptions ++= Seq(
  "-Xfatal-warnings",
  "-Wunused"
)

enablePlugins(ScalafmtPlugin)
