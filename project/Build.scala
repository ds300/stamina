import sbt._
import sbt.Keys._

object Build extends Build {
  import Dependencies._
  import Formatting._

  lazy val basicSettings = Seq(
    organization := "com.scalapenos",
    version := "0.1.0",
    scalaVersion := "2.11.5",
    // crossScalaVersions := Seq("2.11.5", "2.10.4"),
    // crossVersion := CrossVersion.binary,
    scalacOptions := basicScalacOptions,
    incOptions := incOptions.value.withNameHashing(true)
  )

  lazy val libSettings = basicSettings ++ formattingSettings

  lazy val root = Project("stamina", file("."))
    .settings(basicSettings: _*)
    .aggregate(
      core
    )

  lazy val core = Project("stamina-core", file("stamina-core"))
    .settings(libSettings: _*)
    .settings(libraryDependencies ++=
      compile(
        akkaActor,
        sprayJson,
        jsonLenses
      ) ++
      provided(
        scalaReflect(scalaVersion.value)
      ) ++
      test(
        scalatest
      )
    )

  val basicScalacOptions = Seq(
    "-encoding", "utf8",
    "-target:jvm-1.7",
    "-feature",
    "-unchecked",
    "-deprecation",
    "-language:_",
    "-Xlint",
    "-Xlog-reflective-calls"
  )

  val fussyScalacOptions = basicScalacOptions ++ Seq(
    "-Ywarn-unused",
    "-Ywarn-unused-import"
  )
}
