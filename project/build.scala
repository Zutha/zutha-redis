import sbt._
import Keys._

object ZuthaBuild extends Build {

  lazy val root = Project("zutha-redis", file(".")) settings(coreSettings : _*)

  lazy val commonSettings: Seq[Setting[_]] = Seq(
    organization := "net.zutha.redishost",
    version := "0.0.2-SNAPSHOT",
    scalaVersion := "2.10.0-RC5",

    scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-language:postfixOps"),

    resolvers ++= Seq(
      "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository",
      "Local Ivy Repository" at "file://"+Path.userHome.absolutePath+".ivy2/local",
      "Diff Match Patch" at "http://google-diff-match-patch.googlecode.com/svn/trunk/maven"
    )
  )

  lazy val coreSettings = commonSettings ++ Seq(
    name := "zutha-redis",

    libraryDependencies <<= scalaVersion {v => Seq(
      "org.scala-lang" % "scala-reflect" % v,
      "net.debasishg" % "redisclient_2.10" % "[2.8,)",
//      "org.scalatest" %  "scalatest_2.10.0-RC5" % "2.0.M4" % "test",
      "diff_match_patch" % "diff_match_patch" % "current"
    )}
  )
}
