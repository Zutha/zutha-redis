import sbt._
import Keys._

object ZuthaBuild extends Build {

  lazy val root = Project("zutha-redis", file(".")) settings(coreSettings : _*)

  lazy val commonSettings: Seq[Setting[_]] = Seq(
    organization := "net.zutha.redishost",
    version := "0.0.2-SNAPSHOT",
    scalaVersion := "2.10.0-RC2",
    crossScalaVersions := Seq("2.10.0-RC2", "2.9.2"),

    scalacOptions <++= scalaVersion.map {sv =>
      if (sv contains "2.10") Seq("-deprecation", "-unchecked", "-feature", "-language:postfixOps")
      else Seq("-deprecation", "-unchecked")
    },

    resolvers ++= Seq(
      "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository",
      "Local Ivy Repository" at "file://"+Path.userHome.absolutePath+".ivy2/local",
      "Diff Match Patch" at "http://google-diff-match-patch.googlecode.com/svn/trunk/maven"
    )
  )

  lazy val coreSettings = commonSettings ++ Seq(
    name := "zutha-redis",

    libraryDependencies <<= scalaVersion {v =>
      if (v contains "2.10")
        Seq(
      	  "net.debasishg" % "redisclient_2.10" % "[2.8,)",
      	  "diff_match_patch" % "diff_match_patch" % "current",
          "org.scalatest" %  "scalatest_2.10.0-RC2" % "2.0.M4" % "test",
          "com.intellij" % "annotations" % "9.0.4"
        )
      else
        Seq(
        	"net.debasishg" % "redisclient_2.9.2" % "[2.7,)",
        	"diff_match_patch" % "diff_match_patch" % "current",
          "org.scala-lang" %  "scala-library" % v,
          "org.scalatest" % ("scalatest_" + v) % "2.0.M4" % "test",
          "com.intellij" % "annotations" % "9.0.4"
        )
    }
  )
}
