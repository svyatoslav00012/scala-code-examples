ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "scala-code-examples"
  )

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-effect" % "3.3.8",

  "co.fs2" %% "fs2-core" % "3.2.5",
  "co.fs2" %% "fs2-io" % "3.2.5",

  "org.scalatest" %% "scalatest" % "3.2.11",
  "org.scalamock" %% "scalamock" % "5.2.0"
)
