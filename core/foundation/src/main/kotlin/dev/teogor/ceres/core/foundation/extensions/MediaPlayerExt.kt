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

package dev.teogor.ceres.core.foundation.extensions

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.view.SurfaceHolder
import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.teogor.ceres.core.foundation.compositions.LocalMediaPlayer

@Composable
fun createMediaPlayer(
  @RawRes resid: Int,
  audioAttributes: AudioAttributes? = null,
  audioSessionId: Int = 0,
): MediaPlayer {
  val mediaPlayerUtils = LocalMediaPlayer.current
  return remember(resid, audioAttributes, audioSessionId) {
    if (audioAttributes != null) {
      mediaPlayerUtils.create(resid, audioAttributes, audioSessionId)
    } else {
      mediaPlayerUtils.create(resid)
    }
  }
}

@Composable
fun createMediaPlayer(
  uri: Uri,
  holder: SurfaceHolder? = null,
  audioAttributes: AudioAttributes? = null,
  audioSessionId: Int = 0,
): MediaPlayer {
  val mediaPlayerUtils = LocalMediaPlayer.current
  return remember(uri, holder, audioAttributes, audioSessionId) {
    if (holder != null && audioAttributes != null) {
      mediaPlayerUtils.create(uri, holder, audioAttributes, audioSessionId)
    } else if (holder != null) {
      mediaPlayerUtils.create(uri, holder)
    } else {
      mediaPlayerUtils.create(uri)
    }
  }
}
