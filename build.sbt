name := "zutha-redis"

version := "0.0.1-SNAPSHOT"

organization := "net.zutha"

scalaVersion := "2.9.2"

// for Sbt Eclipse
EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Managed

EclipseKeys.withSource := true

resolvers ++= Seq(
  "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository",
  "Local Ivy Repository" at "file://"+Path.userHome.absolutePath+".ivy2/local",
  "Diff Match Patch" at "http://google-diff-match-patch.googlecode.com/svn/trunk/maven"
)

libraryDependencies ++= {
  Seq(
  "net.debasishg" % "redisclient_2.9.2" % "[2.7,)",
  "diff_match_patch" % "diff_match_patch" % "current"
)}

