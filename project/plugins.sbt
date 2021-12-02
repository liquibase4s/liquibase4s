// Makes our code tidy
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.3")

// Builds github actions
addSbtPlugin("com.codecommit" % "sbt-github-actions" % "0.14.1")

// Release plugin
addSbtPlugin("com.geirsson" % "sbt-ci-release" % "1.5.7")

// Code coverage
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.8.2")
