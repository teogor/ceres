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

package dev.teogor.ceres.monetisation.admob.formats

object AdCache {
  private val adCache: MutableMap<String, MutableList<CacheAdModel>> = mutableMapOf()

  @Synchronized
  fun cacheAd(adId: String, ad: CacheAdModel) {
    adCache.getOrPut(adId) {
      mutableListOf()
    }.apply {
      add(ad)
    }
  }

  @Synchronized
  fun getAd(adId: String): CacheAdModel? {
    return adCache[adId]?.firstOrNull()
  }

  @Synchronized
  fun getAds(adId: String, count: Int): List<CacheAdModel> {
    return adCache[adId]?.take(count) ?: emptyList()
  }

  @Synchronized
  fun removeAd(adId: String) {
    adCache[adId]?.let { adList ->
      if (adList.isNotEmpty()) {
        adList.removeAt(0)
      }
      if (adList.isEmpty()) {
        adCache.remove(adId)
      }
    }
  }

  @Synchronized
  fun getAdCount(adId: String) = adCache[adId]?.size ?: 0
}
