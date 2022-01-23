// Makes our code tidy
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.3")

// Builds github actions
addSbtPlugin("com.codecommit" % "sbt-github-actions" % "0.12.0")

// Release plugin
addSbtPlugin("com.github.sbt" % "sbt-ci-release" % "1.5.10")

// Code coverage
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.8.2")
