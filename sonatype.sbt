sonatypeProfileName := "io.megl"
publishMavenStyle := true
licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))
import xerial.sbt.Sonatype._
sonatypeProjectHosting := Some(GitHubHosting("aparo", "zio-json-extra", "alberto.paro@gmail.com"))
