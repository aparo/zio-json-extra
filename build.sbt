import sbtcrossproject.{ CrossType }
import ReleaseTransformations._

publish / skip := true

inThisBuild(
  Seq(
    organization := "io.megl",
    homepage := Some(url("https://github.com/aparo/zio-json-extra.git")),
    licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    developers := List(
      Developer(
        "aparo",
        "Alberto Paro",
        "albeto.paro@gmail.com",
        url("https://github.com/aparo")
      )
    ),
    ThisBuild / versionScheme := Some("early-semver"),
    parallelExecution := false,
    scalafmtOnCompile := false
  )
)

lazy val root =
  project
    .in(file("."))
    .settings((publish / skip) := true)
    .aggregate(
      `zio-json-extra-jvm`,
      `zio-json-extra-js`,
//      `zio-json-diffson-jvm`,
//      `zio-json-diffson-js`,
      `zio-json-exception-jvm`,
      `zio-json-exception-js`
    )
    .dependsOn(
      `zio-json-extra-jvm`,
      `zio-json-extra-js`,
//      `zio-json-diffson-jvm`,
//      `zio-json-diffson-js`,
      `zio-json-exception-jvm`,
      `zio-json-exception-js`
    )

lazy val `zio-json-extra` = ProjectUtils
  .setupCrossModule("zio-json-extra", CrossType.Pure)
  .settings(
    moduleName := "zio-json-extra"
  )
  .settings(Dependencies.zioJsonExtra)
  .settings(Dependencies.testSupport)

lazy val `zio-json-extra-jvm` = `zio-json-extra`.jvm
lazy val `zio-json-extra-js`  = `zio-json-extra`.js

lazy val `zio-json-diffson` = ProjectUtils
  .setupCrossModule("zio-json-diffson", CrossType.Pure)
  .settings(
    moduleName := "zio-json-diffson"
  )
  .settings(Dependencies.zioJsonDiffson)
  .settings(Dependencies.testSupport)
  .dependsOn(`zio-json-extra`)

lazy val `zio-json-diffson-jvm` = `zio-json-diffson`.jvm
lazy val `zio-json-diffson-js`  = `zio-json-diffson`.js

lazy val `zio-json-exception` = ProjectUtils
  .setupCrossModule("zio-json-exception", CrossType.Pure)
  .settings(
    moduleName := "zio-json-exception"
  )
  .settings(Dependencies.testSupport)
  .dependsOn(`zio-json-extra`)

lazy val `zio-json-exception-jvm` = `zio-json-exception`.jvm
lazy val `zio-json-exception-js`  = `zio-json-exception`.js

// Releasing
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommandAndRemaining("publish"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)
