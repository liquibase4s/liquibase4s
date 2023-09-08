// Makes our code tidy
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.5.0")

// Builds github actions
addSbtPlugin("com.github.sbt" % "sbt-github-actions" % "0.16.0")

// Release plugin
addSbtPlugin("com.github.sbt" % "sbt-ci-release" % "1.5.11")

// Code coverage
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "2.0.7")
