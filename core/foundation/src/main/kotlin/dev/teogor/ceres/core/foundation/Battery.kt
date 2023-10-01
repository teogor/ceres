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

package dev.teogor.ceres.core.foundation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.PowerManager
import androidx.annotation.RequiresApi
import dev.teogor.ceres.core.foundation.utils.getSystemService

private var Impl: BatteryImpl? = null

private fun getBatteryImpl(context: Context) =
  Impl ?: if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    BatteryApi23(context)
  } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
    BatteryApi21(context)
  } else {
    BatteryBase(context)
  }.also { Impl = it }

fun Context.batteryUtils(): BatteryUtils {
  return BatteryUtils(getBatteryImpl(this))
}

interface BatteryImpl {
  fun getBatteryManager(): BatteryManager
  fun getPowerManager(): PowerManager
  fun getBatteryLevel(): Int
  fun isCharging(): Boolean
}

class BatteryBase(private val context: Context) : BatteryImpl {
  override fun getBatteryManager(): BatteryManager {
    return context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
  }

  override fun getPowerManager(): PowerManager {
    return context.getSystemService(Context.POWER_SERVICE) as PowerManager
  }

  override fun getBatteryLevel(): Int {
    return getBatteryManager().getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
  }

  override fun isCharging(): Boolean {
    val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
      context.registerReceiver(null, ifilter)
    }
    val status = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
    return status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
  }
}

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class BatteryApi21(private val context: Context) : BatteryImpl {
  override fun getBatteryManager(): BatteryManager {
    return context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
  }

  override fun getPowerManager(): PowerManager {
    return context.getSystemService(Context.POWER_SERVICE) as PowerManager
  }

  override fun getBatteryLevel(): Int {
    val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
      context.registerReceiver(null, ifilter)
    }
    return batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
  }

  override fun isCharging(): Boolean {
    val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
      context.registerReceiver(null, ifilter)
    }
    val status = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
    return status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
  }
}

@RequiresApi(api = Build.VERSION_CODES.M)
class BatteryApi23(private val context: Context) : BatteryImpl {
  override fun getBatteryManager(): BatteryManager {
    return context.getSystemService<BatteryManager>()
  }

  override fun getPowerManager(): PowerManager {
    return context.getSystemService<PowerManager>()
  }

  override fun getBatteryLevel(): Int {
    val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
      context.registerReceiver(null, ifilter)
    }
    return batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
  }

  override fun isCharging(): Boolean {
    val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
      context.registerReceiver(null, ifilter)
    }
    val status = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
    return status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
  }
}

class BatteryUtils(
  private val batteryImpl: BatteryImpl,
) {
  val batteryLevel: Int
    get() = batteryImpl.getBatteryLevel()

  val isCharging: Boolean
    get() = batteryImpl.isCharging()
}
