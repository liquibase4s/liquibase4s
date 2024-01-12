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
    scalaVersion := "3.2.1",
    crossScalaVersions := Seq("2.12.17", "2.13.10", "3.2.1"),
  ),
)

ThisBuild / githubWorkflowTargetTags ++= Seq("v*")
ThisBuild / githubWorkflowPublishTargetBranches := Seq(RefPredicate.StartsWith(Ref.Tag("v")))
ThisBuild / githubWorkflowPublish := Seq(WorkflowStep.Sbt(List("ci-release")))
ThisBuild / githubWorkflowBuild :=
  Seq(
    WorkflowStep.Sbt(List("validate"), name = Some("Build project")),
    WorkflowStep.Use(UseRef.Public("codecov", "codecov-action", "v3"), name = Some("Codecov")),
  )

val CatsVersion = "2.9.0"
val CatsEffectVersion = "3.4.8"
val LiquibaseVersion = "4.25.1"
val ScalaCollectionCompatVersion = "2.9.0"

val MunitVersion = "0.7.29"
val MunitCatsEffectVersion = "1.0.7"
val H2Version = "2.1.214"

val testSettings = Seq(
  testFrameworks += new TestFramework("munit.Framework"),
  Test / fork := true,
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
      "org.typelevel" %% "cats-effect" % CatsEffectVersion,
      "org.scalameta" %% "munit" % MunitVersion % Test,
      "org.typelevel" %% "munit-cats-effect-3" % MunitCatsEffectVersion % Test,
      "com.h2database" % "h2" % H2Version % Test,
    ),
  )

ThisBuild / scalacOptions ++= {
  Seq(
    "-encoding",
    "UTF-8",
    "-language:implicitConversions",
    "-Xfatal-warnings",
    "-deprecation",
    "-feature",
  ) ++
    (CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((3, _)) =>
        Seq(
          "-unchecked",
        )
      case Some((2, n)) if n >= 13 =>
        Seq(
          "-Wunused",
          "-Wunused:imports,privates,locals",
          "-Wvalue-discard",
        )
      case _ =>
        Seq(
          "-language:higherKinds",
        )
    })
}

def addCommandsAlias(name: String, cmds: Seq[String]) =
  addCommandAlias(name, cmds.mkString(";", ";", ""))

addCommandsAlias(
  "validate",
  Seq(
    "clean",
    "scalafmtCheck",
    "scalafmtSbtCheck",
    "test:scalafmtCheck",
    "coverage",
    "test",
    "coverageReport",
    "doc",
    "package",
    "packageSrc",
  ),
)

enablePlugins(ScalafmtPlugin)
