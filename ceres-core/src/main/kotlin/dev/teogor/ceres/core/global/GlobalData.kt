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

package dev.teogor.ceres.core.global

import android.app.Activity
import android.app.Application
import android.content.Context
import java.lang.ref.WeakReference

object GlobalData {

  private lateinit var activityWeak: WeakReference<Activity>

  private lateinit var appWeak: WeakReference<Application>

  var activity: Activity
    set(value) {
      activityWeak = WeakReference(value)
    }
    get() {
      return activityWeak.get()!!
    }

  var app: Application
    set(value) {
      appWeak = WeakReference(value)
    }
    get() {
      return appWeak.get()!!
    }

  val context: Context
    get() {
      return Impl.getContextImpl()
    }

  val themedContext: Context
    get() {
      return Impl.getThemedContextImpl()
    }

  val baseContext: Context
    get() {
      return Impl.getBaseContextImpl()
    }

  @Deprecated(
    message = "deprecated in favour of `context`",
    replaceWith = ReplaceWith("GlobalData.context")
  )
  fun context(): Context = Impl.getContextImpl()

  @Deprecated(
    message = "deprecated in favour of `themedContext`",
    replaceWith = ReplaceWith("GlobalData.themedContext")
  )
  fun themedContext(): Context = Impl.getThemedContextImpl()

  @Deprecated(
    message = "deprecated in favour of `baseContext`",
    replaceWith = ReplaceWith("GlobalData.baseContext")
  )
  fun baseContext(): Context = Impl.getBaseContextImpl()

  internal object Impl {
    internal fun getContextImpl(): Context {
      return app
    }

    internal fun getThemedContextImpl(): Context {
      val currentActivity = activity
      val rootView = currentActivity.window.decorView.rootView
      return rootView.context
    }

    internal fun getBaseContextImpl(): Context {
      return activity.baseContext
    }
  }
}
