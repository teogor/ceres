/*
 * Copyright 2023 teogor (Teodor Grigor)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.teogor.ceres.core.register

@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Target(
  // function declarations
  // @Composable fun Foo() { ... }
  // lambda expressions
  // val foo = @Composable { ... }
  AnnotationTarget.FUNCTION,

  // type declarations
  // var foo: @Composable () -> Unit = { ... }
  // parameter types
  // foo: @Composable () -> Unit
  AnnotationTarget.TYPE,

  // composable types inside of type signatures
  // foo: (@Composable () -> Unit) -> Unit
  AnnotationTarget.TYPE_PARAMETER,

  // composable property getters and setters
  // val foo: Int @Composable get() { ... }
  // var bar: Int
  //   @Composable get() { ... }
  AnnotationTarget.PROPERTY_GETTER,
)
annotation class Registrable
