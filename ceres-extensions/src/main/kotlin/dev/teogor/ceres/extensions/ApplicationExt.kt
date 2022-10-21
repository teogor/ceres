/*
 * Copyright 2022 teogor (Teodor Grigor) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.teogor.ceres.extensions

import android.app.Activity
import android.app.Application
import android.os.Bundle

private typealias onActivityCreated = (activity: Activity, savedInstanceState: Bundle?) -> Unit
private typealias onActivityStarted = (activity: Activity) -> Unit
private typealias onActivityResumed = (activity: Activity) -> Unit
private typealias onActivityPaused = (activity: Activity) -> Unit
private typealias onActivityStopped = (activity: Activity) -> Unit
private typealias onActivitySaveInstanceState = (activity: Activity, outState: Bundle) -> Unit
private typealias onActivityDestroyed = (activity: Activity) -> Unit

fun Application.activityLifecycle(
  created: onActivityCreated? = null,
  started: onActivityStarted? = null,
  resumed: onActivityResumed? = null,
  paused: onActivityPaused? = null,
  stopped: onActivityStopped? = null,
  saveInstanceState: onActivitySaveInstanceState? = null,
  destroyed: onActivityDestroyed? = null
): Application.ActivityLifecycleCallbacks {
  val listener = object : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
      created?.invoke(activity, savedInstanceState)
    }

    override fun onActivityStarted(activity: Activity) {
      started?.invoke(activity)
    }

    override fun onActivityResumed(activity: Activity) {
      resumed?.invoke(activity)
    }

    override fun onActivityPaused(activity: Activity) {
      paused?.invoke(activity)
    }

    override fun onActivityStopped(activity: Activity) {
      stopped?.invoke(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
      saveInstanceState?.invoke(activity, outState)
    }

    override fun onActivityDestroyed(activity: Activity) {
      destroyed?.invoke(activity)
    }
  }
  registerActivityLifecycleCallbacks(listener)
  return listener
}

fun Application.doOnActivityCreated(action: onActivityCreated):
  Application.ActivityLifecycleCallbacks =
  activityLifecycle(created = action)

fun Application.doOnActivityStarted(action: onActivityStarted):
  Application.ActivityLifecycleCallbacks =
  activityLifecycle(started = action)

fun Application.doOnActivityResumed(action: onActivityResumed):
  Application.ActivityLifecycleCallbacks =
  activityLifecycle(resumed = action)

fun Application.doOnActivityPaused(action: onActivityPaused):
  Application.ActivityLifecycleCallbacks =
  activityLifecycle(paused = action)

fun Application.doOnActivityStopped(action: onActivityStopped):
  Application.ActivityLifecycleCallbacks =
  activityLifecycle(stopped = action)

fun Application.doOnActivitySaveInstanceState(action: onActivitySaveInstanceState):
  Application.ActivityLifecycleCallbacks =
  activityLifecycle(saveInstanceState = action)

fun Application.doOnActivityDestroyed(action: onActivityDestroyed):
  Application.ActivityLifecycleCallbacks =
  activityLifecycle(destroyed = action)
