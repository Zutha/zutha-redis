name := "zutha-redis"

version := "0.0.2-SNAPSHOT"

organization := "net.zutha"

//scalaVersion := "2.10.0-M4"

scalaVersion := "2.9.2"

resolvers ++= Seq(
  "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository",
  "Local Ivy Repository" at "file://"+Path.userHome.absolutePath+".ivy2/local",
  "Diff Match Patch" at "http://google-diff-match-patch.googlecode.com/svn/trunk/maven"
)

libraryDependencies ++= {
  Seq(
  "net.debasishg" % "redisclient_2.9.2" % "[2.7,)",
  "org.scalatest" %% "scalatest" % "1.8" % "test",
//  "org.scalatest" % "scalatest_2.10.0-M4" % "1.9-2.10.0-M4-B2" % "test",
  "diff_match_patch" % "diff_match_patch" % "current"
)}

