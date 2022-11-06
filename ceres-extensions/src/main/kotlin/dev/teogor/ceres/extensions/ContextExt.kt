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
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewTreeLifecycleOwner
import kotlin.math.roundToInt
import kotlin.reflect.KClass

/**
 * Returns the color for the given attribute.
 *
 * @param resource the attribute.
 * @param alphaFactor the alpha number [0,1].
 */
@ColorInt
fun Context.getResourceColor(@AttrRes resource: Int, alphaFactor: Float = 1f): Int {
  val typedArray = obtainStyledAttributes(intArrayOf(resource))
  val color = typedArray.getColor(0, 0)
  typedArray.recycle()

  if (alphaFactor < 1f) {
    val alpha = (color.alpha * alphaFactor).roundToInt()
    return Color.argb(alpha, color.red, color.green, color.blue)
  }

  return color
}

fun Context.hideKeyboard(view: View) {
  val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
  inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

val Context.displayMetrics: DisplayMetrics
  get() {
    val displayMetrics = DisplayMetrics()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      display?.apply {
        getRealMetrics(displayMetrics)
      }
    } else {
      val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
      windowManager.defaultDisplay.getMetrics(displayMetrics)
    }

    return displayMetrics
  }

@ColorInt
fun Context.asColor(@ColorRes id: Int): Int {
  return ContextCompat.getColor(this, id)
}

fun View.findViewTreeLifecycleOwner(): LifecycleOwner? = ViewTreeLifecycleOwner.get(this)

fun Context.launchActivity(
  activityClass: KClass<*>,
  flags: Int = 0,
  intentTransformer: Intent.() -> Unit = {}
) {
  this.launchActivity(
    activityClass = activityClass.java,
    flags = flags,
    intentTransformer = intentTransformer
  )
}

fun Context.launchActivity(
  activityClass: Class<*>,
  flags: Int = 0,
  intentTransformer: Intent.() -> Unit = {}
) {
  val intent = Intent(this, activityClass).apply {
    addFlags(flags)
    intentTransformer()
  }
  this.startActivity(intent)
}
