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
import android.media.AudioManager
import android.os.Build
import androidx.annotation.DoNotInline
import androidx.annotation.RequiresApi
import dev.teogor.ceres.core.foundation.utils.getSystemService

private var Impl: AudioManagerImpl? = null

private fun getImpl() = Impl ?: if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
  AudioManagerApi23()
} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
  AudioManagerApi21()
} else {
  AudioManagerBase()
}.also { Impl = it }

fun Context.audioManagerUtils(): AudioManagerUtils {
  return AudioManagerUtils(this, getImpl())
}

interface AudioManagerImpl {
  @DoNotInline
  fun getAudioManager(
    context: Context,
  ): AudioManager
}

private class AudioManagerBase : AudioManagerImpl {
  @DoNotInline
  override fun getAudioManager(context: Context): AudioManager {
    return context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
  }
}

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
private class AudioManagerApi21 : AudioManagerImpl {
  @DoNotInline
  override fun getAudioManager(context: Context): AudioManager {
    return context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
  }
}

@RequiresApi(api = Build.VERSION_CODES.M)
private class AudioManagerApi23 : AudioManagerImpl {
  @DoNotInline
  override fun getAudioManager(context: Context): AudioManager {
    return context.getSystemService<AudioManager>()
  }
}

class AudioManagerUtils(
  context: Context,
  private val audioManagerImpl: AudioManagerImpl,
) {
  val audioManager = audioManagerImpl.getAudioManager(context)

  fun playSoundEffect(effectType: Int) {
    audioManager.playSoundEffect(effectType)
  }

  fun playSoundEffect(effectType: Int, volume: Float) {
    audioManager.playSoundEffect(effectType, volume)
  }
}
