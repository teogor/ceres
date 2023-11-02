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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember

inline fun <reified T> intrinsicImplementation(): Nothing {
  intrinsicImplementation(T::class.java.simpleName)
}

fun intrinsicImplementation(provider: String): Nothing {
  throw NotImplementedError(
    """
    No instance of $provider found. Please provide a $provider using the syntax:
    $provider provides ${provider[0].lowercase()}${provider.substring(1)}
    """.trimIndent(),
  )
}

@Stable
sealed class RegistryLocal<T>(defaultFactory: () -> T) {
  internal val defaultValueHolder = LazyValueHolder(defaultFactory)

  internal abstract fun provided(value: T): State<T>

  val current: T
    get() = currentRegister.consume(this)
}

@Stable
abstract class ProvidableRegistryLocal<T> internal constructor(
  defaultFactory: () -> T,
) : RegistryLocal<T>(defaultFactory) {

  infix fun provide(value: T) = ProvidedValue(this, value)

  @Composable
  infix fun provideComposable(value: T) = remember(value) {
    ProvidedValue(this, value)
  }
}

internal class StaticProvidableRegistryLocal<T>(
  defaultFactory: () -> T,
) : ProvidableRegistryLocal<T>(defaultFactory) {
  override fun provided(value: T): State<T> = StaticValueHolder(value)
}

fun <T> staticRegistryLocalOf(
  defaultFactory: () -> T,
): ProvidableRegistryLocal<T> = StaticProvidableRegistryLocal(defaultFactory)
