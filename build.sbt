import android.Keys._
import android.Dependencies.{LibraryDependency, aar}
import Libraries.macroid._

android.Plugin.androidBuild

platformTarget in Android := "android-23"

name := "macroid-starter"

scalaVersion := "2.11.7"

run <<= run in Android

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "jcenter" at "http://jcenter.bintray.com"
)

scalacOptions in (Compile, compile) ++=
  (dependencyClasspath in Compile).value.files.map("-P:wartremover:cp:" + _.toURI.toURL)

scalacOptions in (Compile, compile) ++= Seq(
  "-P:wartremover:traverser:macroid.warts.CheckUi"
)

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")
scalacOptions ++= Seq("-feature", "-deprecation", "-target:jvm-1.7")
libraryDependencies ++= Seq(
  aar(macroidRoot),
  aar(macroidViewable),
  aar("com.android.support" % "support-v4" % "23.2.0"),
  aar("com.android.support" % "appcompat-v7" % "23.2.0"),
  aar("com.android.support" % "design" % Versions.androidV),
  compilerPlugin("org.brianmckenna" %% "wartremover" % "0.10")
)

proguardScala in Android := true

proguardOptions in Android ++= Settings.proguardCommons ++ Settings.proguardAkka
