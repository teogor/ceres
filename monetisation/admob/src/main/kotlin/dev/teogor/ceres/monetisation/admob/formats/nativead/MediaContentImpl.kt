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

package dev.teogor.ceres.monetisation.admob.formats.nativead

import android.graphics.drawable.Drawable
import com.google.android.gms.ads.MediaContent
import com.google.android.gms.ads.VideoController

class MediaContentImpl : MediaContent {
  override fun getAspectRatio() = 1f

  override fun getCurrentTime() = 0f

  override fun getDuration() = 0f

  override fun getMainImage() = null

  override fun getVideoController(): VideoController {
    TODO("Not yet implemented")
  }

  override fun setMainImage(p0: Drawable?) {
  }

  override fun hasVideoContent() = false

  override fun zza() = null

  override fun zzb() = false
}
