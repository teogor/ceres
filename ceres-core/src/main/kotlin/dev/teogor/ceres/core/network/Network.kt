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

package dev.teogor.ceres.core.network

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.MutableLiveData
import com.zeoflow.startup.ktx.ApplicationInitializer.Companion.context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Network @Inject constructor() {

  val connection = MutableLiveData(connectionLost)

  private val networkRequest: NetworkRequest = NetworkRequest.Builder()
    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
    .build()

  private val networkCallback = object : ConnectivityManager.NetworkCallback() {
    // network is available for use
    override fun onAvailable(network: Network) {
      super.onAvailable(network)

      connection.postValue(connectionAvailable)
    }

    // Network capabilities have changed for the network
    override fun onCapabilitiesChanged(
      network: Network,
      networkCapabilities: NetworkCapabilities
    ) {
      super.onCapabilitiesChanged(network, networkCapabilities)
      val unmetered = networkCapabilities.hasCapability(
        NetworkCapabilities.NET_CAPABILITY_NOT_METERED
      )
      val vpnActive = !networkCapabilities.hasCapability(
        NetworkCapabilities.NET_CAPABILITY_NOT_VPN
      )

      val downSpeed: Int = networkCapabilities.linkDownstreamBandwidthKbps
      val networkQuality: NetworkQuality

      when {
        downSpeed <= 150 -> {
          networkQuality = NetworkQuality.POOR
        }
        downSpeed <= 550 -> {
          networkQuality = NetworkQuality.MODERATE
        }
        downSpeed <= 2000 -> {
          networkQuality = NetworkQuality.GOOD
        }
        downSpeed <= 4000 -> {
          networkQuality = NetworkQuality.STRONG
        }
        else -> {
          networkQuality = NetworkQuality.EXCELLENT
        }
      }

      connection.postValue(
        NetworkData(
          networkStatus = NetworkStatus.CONNECTED,
          networkCapabilities = NetworkCapabilitiesData(
            isUnmetered = unmetered,
            isVpn = vpnActive,
            networkQuality = networkQuality
          )
        )
      )
    }

    // lost network connection
    override fun onLost(network: Network) {
      super.onLost(network)

      connection.postValue(connectionLost)
    }
  }

  fun registerCallback() {
    val connectivityManager = getSystemService(
      context,
      ConnectivityManager::class.java
    ) as ConnectivityManager

    connectivityManager.requestNetwork(
      networkRequest,
      networkCallback
    )
  }
}
