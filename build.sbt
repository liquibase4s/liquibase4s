inThisBuild(
  List(
    organization := "io.github.liquibase4s",
    homepage := Some(url("https://github.com/liquibase4s/liquibase4s")),
    licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    developers := List(
      Developer(
        "rfuerst87",
        "Roman Fürst",
        "r.fuerst@gmx.ch",
        url("https://github.com/rfuerst87"),
      ),
    ),
    scalaVersion := "2.13.4",
    crossScalaVersions := Seq("2.12.11", "2.13.4"),
  ),
)

ThisBuild / githubWorkflowTargetTags ++= Seq("v*")
ThisBuild / githubWorkflowPublishTargetBranches += RefPredicate.StartsWith(Ref.Tag("v"))
ThisBuild / githubWorkflowPublish := Seq(WorkflowStep.Sbt(List("ci-release")))
ThisBuild / githubWorkflowPublish := Seq(
  WorkflowStep.Sbt(
    List("ci-release"),
    env = Map(
      "PGP_PASSPHRASE" -> "${{ secrets.PGP_PASSPHRASE }}",
      "PGP_SECRET" -> "${{ secrets.PGP_SECRET }}",
      "SONATYPE_PASSWORD" -> "${{ secrets.SONATYPE_PASSWORD }}",
      "SONATYPE_USERNAME" -> "${{ secrets.SONATYPE_USERNAME }}",
    ),
  ),
)

val CatsVersion = "2.3.1"
val LiquibaseVersion = "4.2.2"
val ScalaCollectionCompatVersion = "2.4.1"

val MunitVersion = "0.7.21"
val MunitCatsEffectVersion = "0.13.0"
val H2Version = "1.4.200"

val testSettings = Seq(
  testFrameworks += new TestFramework("munit.Framework"),
  fork in Test := true,
)

lazy val root = project
  .in(file("."))
  .settings(name := "liquibase4s")
  .settings(publish / skip := true)
  .aggregate(
    core,
    catsEffect,
  )

lazy val core = project
  .in(file("liquibase4s-core"))
  .settings(testSettings)
  .settings(
    name := "liquibase4s-core",
    libraryDependencies ++= Seq(
      "org.liquibase" % "liquibase-core" % LiquibaseVersion,
      "org.scalameta" %% "munit" % MunitVersion % Test,
      "com.h2database" % "h2" % H2Version % Test,
      "org.scala-lang.modules" %% "scala-collection-compat" % ScalaCollectionCompatVersion % Compile,
    ),
  )

lazy val catsEffect = project
  .in(file("liquibase4s-cats-effect"))
  .dependsOn(core % "compile->compile;test->test")
  .settings(testSettings)
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

ThisBuild / scalacOptions ++=
  Seq(
    "-Xfatal-warnings",
    "-deprecation",
    "-feature",
    "-unchecked",
  ) ++
    (CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, n)) if n >= 13 =>
        Seq(
          "-Wunused",
        )
      case _ =>
        Seq(
          "-language:higherKinds",
        )
    })

enablePlugins(ScalafmtPlugin)
