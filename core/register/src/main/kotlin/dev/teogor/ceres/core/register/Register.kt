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

import androidx.compose.runtime.State

class ProvidedValue<T> internal constructor(
  val registryLocal: RegistryLocal<T>,
  val value: T,
) {
  init {
    currentRegister.provide(this)
  }
}

internal object LocalRegistryCache {
  private val localMap: MutableMap<RegistryLocal<*>, State<*>> = mutableMapOf()

  fun <T> getValue(local: RegistryLocal<T>): State<T> {
    @Suppress("UNCHECKED_CAST")
    return localMap[local] as? State<T> ?: intrinsicImplementation(local.javaClass.simpleName)
  }

  fun <T> setValue(local: RegistryLocal<T>, value: State<T>) {
    localMap[local] = value
  }
}

sealed interface Register {
  val localMap: MutableMap<RegistryLocal<*>, State<*>>

  fun <T> consume(key: RegistryLocal<T>): T

  fun <T> provide(provider: ProvidedValue<T>)

  fun <T> provides(vararg providers: ProvidedValue<T>)
}

data object RegisterImpl : Register {
  override val localMap: MutableMap<RegistryLocal<*>, State<*>> = mutableMapOf()

  @Suppress("UNCHECKED_CAST")
  override fun <T> consume(key: RegistryLocal<T>): T {
    return localMap[key]?.value as? T ?: intrinsicImplementation(key.javaClass.simpleName)
  }

  override fun <T> provide(provider: ProvidedValue<T>) {
    val key = provider.registryLocal
    val value = StaticValueHolder(provider.value)
    localMap[key] = value
  }

  override fun <T> provides(vararg providers: ProvidedValue<T>) {
    providers.forEach {
      provide(it)
    }
  }
}
