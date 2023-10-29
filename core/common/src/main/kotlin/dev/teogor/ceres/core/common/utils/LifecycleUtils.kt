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

package dev.teogor.ceres.core.common.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

/**
 * Composable function to observe the lifecycle events of the current LifecycleOwner
 * and execute the provided [onEvent] lambda when a lifecycle event occurs.
 *
 * @param onEvent Lambda function to handle lifecycle events with parameters [owner]
 *                and [event], where [owner] is the LifecycleOwner and [event] is
 *                the Lifecycle.Event.
 */
@Composable
fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
  val eventHandler = rememberUpdatedState(onEvent)
  val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

  DisposableEffect(lifecycleOwner.value) {
    val lifecycle = lifecycleOwner.value.lifecycle
    val observer = LifecycleEventObserver { owner, event ->
      eventHandler.value(owner, event)
    }

    lifecycle.addObserver(observer)
    onDispose {
      lifecycle.removeObserver(observer)
    }
  }
}

/**
 * Observe the lifecycle of the current Activity. This can be useful for performing actions when
 * the Activity enters or exits the foreground.
 *
 * @param onEnterForeground Callback to execute when the Activity enters the foreground.
 * @param onExitForeground Callback to execute when the Activity exits the foreground.
 */
@Composable
fun ObserveActivityLifecycle(
  onEnterForeground: () -> Unit = {},
  onExitForeground: () -> Unit = {},
) {
  val currentActivity = activity

  currentActivity?.let {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(currentActivity) {
      val lifecycleObserver = object : DefaultLifecycleObserver {
        override fun onStop(owner: LifecycleOwner) {
          super.onStop(owner)
          onExitForeground()
        }

        override fun onStart(owner: LifecycleOwner) {
          super.onStart(owner)
          onEnterForeground()
        }
      }

      val lifecycle = lifecycleOwner.lifecycle
      lifecycle.addObserver(lifecycleObserver)

      onDispose {
        lifecycle.removeObserver(lifecycleObserver)
      }
    }
  }
}

/**
 * Observe the lifecycle of the Application. This can be useful for performing actions when
 * the Application enters or exits the foreground.
 *
 * @param onEnterForeground Callback to execute when the Application enters the foreground.
 * @param onExitForeground Callback to execute when the Application exits the foreground.
 */
@Composable
fun ObserveApplicationLifecycle(
  onEnterForeground: () -> Unit = {},
  onExitForeground: () -> Unit = {},
) {
  val context = LocalContext.current.applicationContext as Application

  DisposableEffect(context) {
    val observer = object : Application.ActivityLifecycleCallbacks {
      override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        // Unused
      }

      override fun onActivityStarted(activity: Activity) {
        // Unused
      }

      override fun onActivityResumed(activity: Activity) {
        onEnterForeground()
      }

      override fun onActivityPaused(activity: Activity) {
        onExitForeground()
      }

      override fun onActivityStopped(activity: Activity) {
        // Unused
      }

      override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        // Unused
      }

      override fun onActivityDestroyed(activity: Activity) {
        // Unused
      }
    }

    context.registerActivityLifecycleCallbacks(observer)

    onDispose {
      context.unregisterActivityLifecycleCallbacks(observer)
    }
  }
}

/**
 * Observe the lifecycle of the current Composable. This can be useful for performing actions when
 * the Composable enters or exits the foreground.
 *
 * @param onEnterForeground Callback to execute when the Composable enters the foreground.
 * @param onExitForeground Callback to execute when the Composable exits the foreground.
 */
@Composable
fun ObserveComposableLifecycle(
  onEnterForeground: () -> Unit = {},
  onExitForeground: () -> Unit = {},
) {
  val lifecycleOwner = LocalLifecycleOwner.current

  DisposableEffect(Unit) {
    val lifecycleObserver = object : DefaultLifecycleObserver {
      override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        onExitForeground()
      }

      override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        onEnterForeground()
      }
    }

    lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

    onDispose {
      lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
    }
  }
}
