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

@file:SuppressLint("ObsoleteSdkInt")

package dev.teogor.ceres.ui.foundation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.DoNotInline
import androidx.annotation.RequiresApi

private var Impl: VibratorImpl? = null

// todo create a module that handles these kind of stuff and is standalone

private fun getImpl() = Impl ?: if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
  VibratorApi31()
} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
  VibratorApi26()
} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
  VibratorApi21()
} else {
  VibratorBase()
}.also { Impl = it }

fun Context.getVibrator(): Vibrator {
  return getImpl().getVibrator(context = this)
}

fun Context.vibrate(
  milliseconds: Long,
  effect: HapticEffect = HapticEffect.DEFAULT_AMPLITUDE,
) {
  val vibrator = getVibrator()
  getImpl().vibrate(vibrator, milliseconds, effect)
}

private interface VibratorImpl {
  @DoNotInline
  fun getVibrator(
    context: Context,
  ): Vibrator

  fun vibrate(
    vibrator: Vibrator,
    milliseconds: Long,
    effect: HapticEffect,
  )
}

private class VibratorBase : VibratorImpl {
  @DoNotInline
  override fun getVibrator(context: Context): Vibrator {
    @Suppress("DEPRECATION")
    return context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
  }

  override fun vibrate(
    vibrator: Vibrator,
    milliseconds: Long,
    effect: HapticEffect,
  ) {
    @Suppress("DEPRECATION")
    vibrator.vibrate(milliseconds)
  }
}

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
private class VibratorApi21 : VibratorImpl {
  @DoNotInline
  override fun getVibrator(context: Context): Vibrator {
    @Suppress("DEPRECATION")
    return context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
  }

  override fun vibrate(
    vibrator: Vibrator,
    milliseconds: Long,
    effect: HapticEffect,
  ) {
    @Suppress("DEPRECATION")
    vibrator.vibrate(milliseconds)
  }
}

@RequiresApi(api = Build.VERSION_CODES.O)
private class VibratorApi26 : VibratorImpl {
  @DoNotInline
  override fun getVibrator(context: Context): Vibrator {
    @Suppress("DEPRECATION")
    return context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
  }

  override fun vibrate(
    vibrator: Vibrator,
    milliseconds: Long,
    effect: HapticEffect,
  ) {
    vibrator.vibrate(VibrationEffect.createOneShot(milliseconds, effect.value))
  }
}

@RequiresApi(api = Build.VERSION_CODES.S)
private class VibratorApi31 : VibratorImpl {

  @DoNotInline
  override fun getVibrator(context: Context): Vibrator {
    val vibratorManager = context.getSystemService<VibratorManager>()
    return vibratorManager.defaultVibrator
  }

  override fun vibrate(
    vibrator: Vibrator,
    milliseconds: Long,
    effect: HapticEffect,
  ) {
    vibrator.vibrate(VibrationEffect.createOneShot(milliseconds, effect.value))
  }
}

@RequiresApi(api = Build.VERSION_CODES.M)
inline fun <reified T : Any> Context.getSystemService(): T =
  getSystemService(this, T::class.java)

@RequiresApi(api = Build.VERSION_CODES.M)
fun <T> getSystemService(context: Context, serviceClass: Class<T>): T {
  return context.getSystemService(serviceClass)
}

enum class HapticEffect(val value: Int) {
  DEFAULT_AMPLITUDE(-1),
  EFFECT_CLICK(0),
  EFFECT_DOUBLE_CLICK(1),
  EFFECT_HEAVY_CLICK(5),
  EFFECT_TICK(2),
}
