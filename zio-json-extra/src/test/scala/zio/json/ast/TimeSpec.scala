/*
 * Copyright 2022-2023 Alberto Paro
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package zio.json.ast
import scala.concurrent.duration._
import zio.json.ast.time._
import zio.json._
import zio.Scope
import zio.test.Assertion._
import zio.test._

object TimeSpec extends ZIOSpecDefault {
  override def spec: Spec[TestEnvironment with Scope, Any] =
    suite("Time")(test("should serialize/deserialize FiniteDuration") {
      val fn     = 5.seconds
      val fnJson = fn.toJson
      assert(fnJson)(equalTo("""{"length":5,"unit":"SECONDS"}"""))
      val value = FiniteDuration(5, "seconds")

      assert(fnJson.fromJson[FiniteDuration])(equalTo(Right(fn)))
    })
}
