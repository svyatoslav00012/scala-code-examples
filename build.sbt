ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "scala-code-examples"
  )

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-effect" % "3.3.11",

  "co.fs2" %% "fs2-core" % "3.2.7",
  "co.fs2" %% "fs2-io" % "3.2.7",

  "io.circe" %% "circe-core" % "0.15.0-M1",
  "io.circe" %% "circe-generic" % "0.15.0-M1",
  "io.circe" %% "circe-parser" % "0.15.0-M1",

  "org.scalatest" %% "scalatest" % "3.2.11",
  "org.scalamock" %% "scalamock" % "5.2.0"
)
