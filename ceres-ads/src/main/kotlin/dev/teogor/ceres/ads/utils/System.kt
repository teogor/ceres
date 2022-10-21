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

package dev.teogor.ceres.ads.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.zeoflow.startup.ktx.ApplicationInitializer

fun isNetworkAvailable(): Boolean {
  val connectivityManager =
    ApplicationInitializer.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

  // For 29 api or above
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
    val capabilities =
      connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        ?: return false
    return when {
      capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
      capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
      capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
      else -> false
    }
  }
  // For below 29 api
  else {
    if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting) {
      return true
    }
  }
  return false
}
