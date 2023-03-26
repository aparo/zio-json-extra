import sbt._
import sbt.Keys._
import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._

object Dependencies {

  lazy val testSupport = Seq(
    libraryDependencies ++= DependencyHelpers.test(
      "org.scalatest" %%% "scalatest"    % Versions.scalaTest,
      "dev.zio"        %% "zio-test"     % Versions.zio,
      "dev.zio"        %% "zio-test-sbt" % Versions.zio
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )

  val crossTest = Def.settings {
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest"    % Versions.scalaTest % "test",
      "dev.zio"       %%% "zio-test"     % Versions.zio       % "test",
      "dev.zio"       %%% "zio-test-sbt" % Versions.zio       % "test"
    )
  }

  val scalaReflect = "org.scala-lang" % "scala-reflect"

  lazy val zioJsonExtra = Def.settings {
    libraryDependencies ++= DependencyHelpers.compile(
      "dev.zio"                %%% "zio"                     % Versions.zio,
      "dev.zio"                %%% "zio-json"                % Versions.zioJson,
      "org.scala-lang.modules" %%% "scala-collection-compat" % "2.9.0",
      "io.github.cquiroz"      %%% "scala-java-time"         % "2.5.0"
    ) ++ (CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, minor)) if minor <= 13 =>
        Seq(
          scalaReflect % (ThisBuild / scalaVersion).value % Provided
        )
      case Some((3, _)) => Seq("com.softwaremill.magnolia1_3" %%% "magnolia" % "1.2.6")
      case _            => Nil
    })

  } ++ crossTest

  lazy val zioJsonDiffson = Def.settings {
    libraryDependencies ++= DependencyHelpers.compile(
      "org.gnieh" %%% "diffson-core" % "4.4.0"
    )
  } ++ crossTest

}
