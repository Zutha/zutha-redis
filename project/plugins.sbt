//Ensime
addSbtPlugin("org.ensime" % "ensime-sbt-cmd" % "[0.1.0,)")

//Intellij Idea
resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.2.0-SNAPSHOT")


// Jrebel

resolvers += "Jawsy.fi M2 releases" at "http://oss.jawsy.fi/maven2/releases"

addSbtPlugin("fi.jawsy.sbtplugins" %% "sbt-jrebel-plugin" % "0.9.0")

seq(jrebelSettings: _*)

jrebel.enabled := true

jrebel.classpath += file("target/scala-2.10/classes")