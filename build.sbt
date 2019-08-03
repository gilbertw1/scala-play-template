name := """scala-play-template"""
organization := "com.bryangilbert"
version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"
val akkaVersion = "2.5.23"

val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
val akkaRemote = "com.typesafe.akka" %% "akka-remote" % akkaVersion
val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % akkaVersion

libraryDependencies ++= Seq(
  guice,
  ws,
  evolutions,
  "org.postgresql" % "postgresql" % "42.1.3",
  "com.typesafe.play" %% "play-slick" % "3.0.1",
  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.1",
  "com.github.tototoshi" %% "slick-joda-mapper" % "2.4.0",
  "joda-time" % "joda-time" % "2.7",
  "org.joda" % "joda-convert" % "1.7",
  "com.typesafe.play" %% "play-json-joda" % "2.7.4",
  "org.scala-lang.modules" %% "scala-async" % "0.10.0",
  "ai.x" %% "play-json-extensions" % "0.40.2",
  "com.github.tminglei" %% "slick-pg" % "0.17.3",
  "com.github.tminglei" %% "slick-pg_joda-time" % "0.17.3",
  "com.github.tminglei" %% "slick-pg_play-json" % "0.17.3",
  akkaActor, akkaRemote, akkaSlf4j, akkaTestkit)

ensimeScalaVersion in ThisBuild := "2.13.0"
