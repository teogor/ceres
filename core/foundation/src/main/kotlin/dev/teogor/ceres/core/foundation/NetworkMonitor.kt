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
@file:Suppress("DEPRECATION")

package dev.teogor.ceres.core.foundation

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.teogor.ceres.core.foundation.utils.getSystemService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import javax.inject.Inject

private var Impl: NetworkConnectivityImpl? = null

private fun getNetworkConnectivityImpl(context: Context) =
  Impl ?: if (Build.VERSION.SDK_INT >= VERSION_CODES.M) {
    NetworkConnectivityApi23(context)
  } else {
    NetworkConnectivityBase(context)
  }.also { Impl = it }

fun Context.networkConnectivityUtils(): NetworkConnectivityUtils {
  return NetworkConnectivityUtils(getNetworkConnectivityImpl(this))
}

interface NetworkConnectivityImpl {
  val isOnline: Flow<Boolean>
}

class NetworkConnectivityBase(private val context: Context) : NetworkConnectivityImpl {

  override val isOnline: Flow<Boolean> = callbackFlow {
    val connectivityManager = context.getSystemService<ConnectivityManager>()

    val callback = object : ConnectivityManager.NetworkCallback() {
      override fun onAvailable(network: Network) {
        channel.trySend(connectivityManager.isCurrentlyConnected())
      }

      override fun onLost(network: Network) {
        channel.trySend(connectivityManager.isCurrentlyConnected())
      }

      override fun onCapabilitiesChanged(
        network: Network,
        networkCapabilities: NetworkCapabilities,
      ) {
        channel.trySend(connectivityManager.isCurrentlyConnected())
      }
    }

    connectivityManager.registerNetworkCallback(
      NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build(),
      callback,
    )

    channel.trySend(connectivityManager.isCurrentlyConnected())

    awaitClose {
      connectivityManager.unregisterNetworkCallback(callback)
    }
  }.conflate()

  private fun ConnectivityManager?.isCurrentlyConnected() = when (this) {
    null -> false
    else -> activeNetworkInfo?.isConnected ?: false
  }
}

@RequiresApi(api = VERSION_CODES.M)
class NetworkConnectivityApi23(private val context: Context) : NetworkConnectivityImpl {
  override val isOnline: Flow<Boolean> = callbackFlow {
    val connectivityManager = context.getSystemService<ConnectivityManager>()

    val callback = object : ConnectivityManager.NetworkCallback() {
      override fun onAvailable(network: Network) {
        channel.trySend(connectivityManager.isCurrentlyConnected())
      }

      override fun onLost(network: Network) {
        channel.trySend(connectivityManager.isCurrentlyConnected())
      }

      override fun onCapabilitiesChanged(
        network: Network,
        networkCapabilities: NetworkCapabilities,
      ) {
        channel.trySend(connectivityManager.isCurrentlyConnected())
      }
    }

    connectivityManager.registerNetworkCallback(
      NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build(),
      callback,
    )

    channel.trySend(connectivityManager.isCurrentlyConnected())

    awaitClose {
      connectivityManager.unregisterNetworkCallback(callback)
    }
  }.conflate()

  private fun ConnectivityManager?.isCurrentlyConnected() = when (this) {
    null -> false
    else ->
      activeNetwork
        ?.let(::getNetworkCapabilities)
        ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        ?: false
  }
}

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {
  @Binds
  fun bindsNetworkMonitor(
    networkMonitor: ConnectivityManagerNetworkMonitor,
  ): NetworkMonitorUtility
}

class ConnectivityManagerNetworkMonitor @Inject constructor(
  @ApplicationContext private val context: Context,
) : NetworkMonitorUtility {
  override val isOnline = context
    .networkConnectivityUtils()
    .isOnline
}

/**
 * Utility for reporting app connectivity status
 */
interface NetworkMonitorUtility {
  val isOnline: Flow<Boolean>
}

class NetworkMonitor {
  var isOffline: Boolean by mutableStateOf(false)
}

class NetworkConnectivityUtils(
  private val networkConnectivityImpl: NetworkConnectivityImpl,
) {
  val isOnline: Flow<Boolean>
    get() = networkConnectivityImpl.isOnline
}
