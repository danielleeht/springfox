/*
 *
 *  Copyright 2017-2019 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */
package springfox.documentation.builders

import spock.lang.Specification
import spock.lang.Unroll

import static com.google.common.collect.Sets.*

class ParameterMergerSpec extends Specification {
  @Unroll
  def "Merges parameters by name"() {
    given:
    def merger = new ParameterMerger(destination, source)

    when:
    def merged = merger.merged()
    def expected = newHashSet()
    expected.addAll(destination.collect { it.name })
    expected.addAll(source.collect { it.name })

    then:
    merged.size() == expected.size()

    where:
    destination          | source
    [param("a", "desc")] | [param("a", "desc2")]
    [param("a", "desc")] | [param("b", "desc2")]
    [param("a", "desc")] | []
    []                   | [param("a", "desc")]
    []                   | []
  }

  def param(String name, String desc) {
    new ParameterBuilder()
        .name(name)
        .description(desc)
        .build()
  }
}
