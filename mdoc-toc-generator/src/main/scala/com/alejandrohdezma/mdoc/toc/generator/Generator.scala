/*
 * Copyright 2020 Scala Steward <https://github.com/scala-steward>
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

package com.alejandrohdezma.mdoc.toc.generator

object Generator {

  def toc(text: String): String =
    text.split('\n').filter(_.startsWith("##")).map {
      case H2(header) => s"- [$header](#${link(header)})"
      case H3(header) => s"  - [$header](#${link(header)})"
      case H4(header) => s"    - [$header](#${link(header)})"
      case H5(header) => s"      - [$header](#${link(header)})"
      case H6(header) => s"        - [$header](#${link(header)})"
      case H7(header) => s"          - [$header](#${link(header)})"
    } mkString ("---\n\n", "\n", "\n")

  def link(header: String): String =
    header
      .replaceAll(" ", "-")
      .replaceAll("""[/?!:\[\]`.,()*"';{}+=<>~$|#@&–—]""", "")
      .replaceAll("[。？！，、；：“”【】（）〔〕［］﹃﹄‘’﹁﹂—…－～《》〈〉「」]", "")
      .toLowerCase

  private val H2 = s"## (.*)".r
  private val H3 = s"### (.*)".r
  private val H4 = s"#### (.*)".r
  private val H5 = s"##### (.*)".r
  private val H6 = s"###### (.*)".r
  private val H7 = s"####### (.*)".r

}
