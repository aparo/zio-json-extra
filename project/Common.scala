import sbt.Keys._
import sbt._
// import scoverage.ScoverageKeys._

import scala.util.Try
import xerial.sbt.Sonatype.autoImport._

object Common {
  val appName = EnvironmentGlobal.appName

  lazy val commonGeneric = Seq(
    homepage := Some(url("https://www.megl.io")),
    licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0")),
    organization := "io.megl",
    scalaVersion := "3.2.1",
    crossScalaVersions := Versions.crossScalaVersions,
    organizationName := "Paro Consulting",
    startYear := Some(2022),
    //    maxErrors := 1,
    // fork := (if (isScalaJSProject.value) false else fork.value),
    // cancelable := true,
    sourcesInBase := false,
    javaOptions +=
      s"-Dmeglio.sbt.root=${(ThisBuild / baseDirectory).value.getCanonicalFile}",
    version := version.value.replace('+', '-'),
    concurrentRestrictions := {

      val limited =
        Try(sys.env.getOrElse("SBT_TASK_LIMIT", "4").toInt).getOrElse {
          throw new IllegalArgumentException(
            "SBT_TASK_LIMIT should be an integer value"
          )
        }
      Seq(Tags.limitAll(limited))
    },
    ivyLoggingLevel := UpdateLogging.Quiet,
    // makes it really easy to use a RAM disk - when the environment variable
    // exists, the SBT_VOLATILE_TARGET/target directory is created as a side
    // effect
    target := {
      sys.env.get("SBT_VOLATILE_TARGET") match {
        case None => target.value
        case Some(base) =>
          file(base) / target.value.getCanonicalPath.replace(':', '_')
      }
    },
    // When the environment variable exists, the
    // SBT_VOLATILE_TARGET/java.io.tmpdir directory is created as a side effect
    javaOptions ++= {
      sys.env.get("SBT_VOLATILE_TARGET") match {
        case None => Nil
        case Some(base) =>
          val tmpdir = s"$base/java.io.tmpdir"
          file(tmpdir).mkdirs()
          s"-Djava.io.tmpdir=$tmpdir" :: Nil
      }
    },
    resolvers ++= {
      val name = EnvironmentGlobal.appName
      val host = EnvironmentGlobal.sonatypeHost
      Seq(
        //        Opts.resolver.mavenLocalFile,
        s"$name Nexus Repository".at(s"$host/repository/maven-releases/")
      )
    },
    credentials ++= (
      for {
        username <- Option(System.getenv().get("SONATYPE_USERNAME"))
        password <- Option(System.getenv().get("SONATYPE_PASSWORD"))
      } yield Credentials(
        "Sonatype Nexus Repository Manager",
        "oss.sonatype.org",
        username,
        password
      )
    ).toSeq,
    scalacOptions ++= (CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, 12)) =>
        Seq(
          "-Xfuture",
          "-Yno-adapted-args",
          "-Ypartial-unification",
          "-Ywarn-unused-import",
          "-Yrangepos"
        )

      case Some((2, 13)) =>
        Seq(
          "-Ymacro-annotations",
          "-Yrangepos"
        )
      case _ => Nil
    })
  ) ++ Licensing.settings

  lazy val commonJvmSettings: Seq[Def.Setting[_]] = Seq(
    scalacOptions ++= Seq(
      "-encoding",
      "utf8",
      "-deprecation",
      "-feature",
      "-unchecked",
      "-language:postfixOps",
      "-language:existentials",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-Ywarn-dead-code",
      "-Ywarn-numeric-widen"
    )
  )

  lazy val commonJsSettings = Seq(
    scalacOptions ++= Seq(
      "-encoding",
      "UTF-8",
      "-deprecation",                  // Emit warning and location for usages of deprecated APIs.
      "-feature",                      // Emit warning and location for usages of features that should be imported explicitly.
      "-language:implicitConversions", // Allow definition of implicit functions called views
      "-language:postfixOps",
      "-language:existentials",
      "-language:higherKinds"
    )
    // coverageEnabled := false,
    // coverageExcludedFiles := ".*",
    // scalaJSStage in Test := FastOptStage,
    // jsEnv in Test := PhantomJSEnv().value,
    // batch mode decreases the amount of memory needed to compile scala.js code
    // scalaJSOptimizerOptions := scalaJSOptimizerOptions.value.withBatchMode(
  )

  lazy val settings = Seq(
    Test / fork := false,
    maxErrors := 1000
  ) ++ Licensing.settings

  lazy val noPublishSettings = Seq(
    publish / skip := true,
    publishArtifact := false,
    publish := {},
    publishLocal := {},
    publishArtifact := false
  )

  //java options only for JVM
  lazy val javaOptionsJVM = Seq(
    "-encoding",
    "UTF-8",
    "-source",
    "1.8",
    "-target",
    "1.8",
    "-XX:+UseG1GC",
    "-XX:MaxGCPauseMillis=20",
    "-XX:InitiatingHeapOccupancyPercent=35",
    "-Dsun.jnu.encoding=UTF-8",
    "-Dfile.encoding=UTF-8",
    "-Djava.awt.headless=true",
    "-Djava.net.preferIPv4Stack=true"
  )

  //scala options only for JVM
  lazy val scalacOptionsJVM = Seq(
    "-encoding",
    "UTF-8",
    "-target:jvm-1.8"
  )

  lazy val publicationSettings = Seq(
    publishTo := sonatypePublishToBundle.value,
    pomExtra :=
      <scm>
        <connection>scm:git:github.com/aparo/zio-json-extra.git</connection>
        <developerConnection>scm:git:github.com/aparo/zio-json-extra.git</developerConnection>
        <url>https://github.com/aparo/zio-json-extra.git</url>
      </scm>
        <developers>
          <developer>
            <id>aparo</id>
            <name>Alberto Paro</name>
          </developer>
        </developers>
  )

  def priorTo2_13(scalaVersion: String): Boolean =
    CrossVersion.partialVersion(scalaVersion) match {
      case Some((2, minor)) if minor < 13 => true
      case _                              => false
    }

}
