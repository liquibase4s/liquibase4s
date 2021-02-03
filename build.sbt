val CatsVersion = "2.3.1"
val LiquibaseVersion = "4.2.2"

val MunitVersion = "0.7.21"
val MunitCatsEffectVersion = "0.13.0"
val H2Version = "1.4.192"

ThisBuild / organization := "io.github.liquibase4s"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.4"
ThisBuild / crossScalaVersions := Seq("2.12.11", "2.13.4")

ThisBuild / testFrameworks += new TestFramework("munit.Framework")
ThisBuild / fork in Test := true

ThisBuild / githubWorkflowPublishTargetBranches := Seq()

lazy val root = project
  .in(file("."))
  .settings(name := "liquibase4s")
  .aggregate(
    core,
    catsEffect,
  )

lazy val core = project
  .in(file("liquibase4s-core"))
  .settings(
    name := "liquibase4s-core",
    libraryDependencies ++= Seq(
      "org.liquibase" % "liquibase-core" % LiquibaseVersion,
      "org.scalameta" %% "munit" % MunitVersion % Test,
      "com.h2database" % "h2" % H2Version % Test,
    ),
  )

lazy val catsEffect = project
  .in(file("liquibase4s-cats-effect"))
  .dependsOn(core % "compile->compile;test->test")
  .settings(
    name := "liquibase4s-cats-effect",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % CatsVersion,
      "org.typelevel" %% "cats-effect" % CatsVersion,
      "org.scalameta" %% "munit" % MunitVersion % Test,
      "org.typelevel" %% "munit-cats-effect-2" % MunitCatsEffectVersion % Test,
      "com.h2database" % "h2" % H2Version % Test,
    ),
  )

scalacOptions ++= Seq(
  "-Xfatal-warnings",
  "-Wunused",
)

enablePlugins(ScalafmtPlugin)
