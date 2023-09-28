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

package dev.teogor.ceres.navigation.core.lib.common

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

@Deprecated(message = "Try to use something from compose")
class SingleLiveEvent<T> : MutableLiveData<T>() {
  private val pending = AtomicBoolean(false)

  companion object {
    private const val TAG = "SingleLiveEvent"
  }

  @MainThread
  override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
    if (hasActiveObservers()) {
      Log.w(TAG, "Multiple observers registered but only one will be notified of changes.")
    }

    super.observe(owner) { t ->
      if (pending.compareAndSet(true, false)) {
        observer.onChanged(t)
      }
    }
  }

  @MainThread
  override fun setValue(t: T?) {
    pending.set(true)
    super.setValue(t)
  }

  @MainThread
  fun call() {
    value = null
  }
}
