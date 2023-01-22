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

import zio.Chunk
import zio.Scope
import zio.test.Assertion._
import zio.test._
import zio.test.ZIOSpecDefault
object CommonASTManipulationSpec extends ZIOSpecDefault {
  override def spec: Spec[TestEnvironment with Scope, Any] =
    suite("Extended ast")(test("deepMerge should merge with correct mode") {
      val json1 = Json.Obj(
        Chunk(
          ("a", Json.Str("1")),
          ("b", Json.Arr(Chunk(Json.Str("2"), Json.Str("3")))),
          ("d", Json.Str("99"))
        )
      )
      val json2 = Json.Obj(
        Chunk(
          ("a", Json.Str("10")),
          ("b", Json.Arr(Chunk(Json.Str("4")))),
          ("c", Json.Str("20"))
        )
      )

      val actual1 = json1.deepMerge(json2)
      val expected1 = Json.Obj(
        Chunk(
          ("a", Json.Str("10")),
          ("b", Json.Arr(Chunk(Json.Str("4")))),
          ("d", Json.Str("99")),
          ("c", Json.Str("20"))
        )
      )

      assert(actual1)(equalTo(expected1))

      val actual2 = json1.deepMerge(json2, mergeMode = MergeMode.Concat)
      val expected2 = Json.Obj(
        Chunk(
          ("a", Json.Str("10")),
          ("b", Json.Arr(Chunk(Json.Str("2"), Json.Str("3"), Json.Str("4")))),
          ("d", Json.Str("99")),
          ("c", Json.Str("20"))
        )
      )

      assert(actual2)(equalTo(expected2))

      val actual3 = json1.deepMerge(json2, mergeMode = MergeMode.Index)
      val expected3 = Json.Obj(
        Chunk(
          ("a", Json.Str("10")),
          ("b", Json.Arr(Chunk(Json.Str("4"), Json.Str("3")))),
          ("d", Json.Str("99")),
          ("c", Json.Str("20"))
        )
      )
      assert(actual3)(equalTo(expected3))
    })
}
