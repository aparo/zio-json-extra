ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always

addSbtPlugin("com.github.gseitz"               % "sbt-release"              % "1.0.13")
addSbtPlugin("com.jsuereth"                    % "sbt-pgp"                  % "2.1.1")
addSbtPlugin("com.typesafe.sbt"                % "sbt-git"                  % "1.0.2")
addSbtPlugin("org.portable-scala"              % "sbt-scalajs-crossproject" % "1.2.0")
addSbtPlugin("org.scala-js"                    % "sbt-scalajs"              % "1.12.0")
addSbtPlugin("org.scalameta"                   % "sbt-scalafmt"             % "2.5.0")
addSbtPlugin("org.scalastyle"                 %% "scalastyle-sbt-plugin"    % "1.0.0")
addSbtPlugin("org.scoverage"                   % "sbt-scoverage"            % "2.0.5")
addSbtPlugin("de.heikoseeberger"               % "sbt-header"               % "5.9.0")
addSbtPlugin("ch.epfl.scala"                   % "sbt-scalafix"             % "0.10.4")
libraryDependencies += "com.github.pathikrit" %% "better-files"             % "3.9.2"
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "3.9.17")
addSbtPlugin("com.github.sbt" % "sbt-pgp" % "2.1.2")