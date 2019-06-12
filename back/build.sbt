name := "back"
 
version := "1.0" 
      
lazy val `back` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
resolvers += "Atlassian Releases" at "https://maven.atlassian.com/public/"
      
scalaVersion := "2.12.2"

//libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "3.0.0",
  "com.typesafe.play" %% "play-json" % "2.6.0",
  "com.h2database" % "h2" % "1.4.199",
  guice,
  /*
  "com.mohiva" %% "play-silhouette" % "5.0.2",
  "com.mohiva" %% "play-silhouette-persistence" % "5.0.2",
  "com.mohiva" %% "play-silhouette-password-bcrypt" % "5.0.2",
  "com.mohiva" %% "play-silhouette-crypto-jca" % "5.0.2",
  */

  "org.scalatestplus.play" %% "scalatestplus-play" % "3.0.0" % "test",
  specs2 % Test

)
