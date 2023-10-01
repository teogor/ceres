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
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.view.SurfaceHolder
import androidx.annotation.DoNotInline
import androidx.annotation.RawRes
import androidx.annotation.RequiresApi

private var Impl: MediaPlayerImpl? = null

private fun getImpl() = Impl ?: if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
  MediaPlayerApi34()
} else {
  MediaPlayerBase()
}.also { Impl = it }

fun Context.mediaPlayerUtils(): MediaPlayerUtils {
  return MediaPlayerUtils(this, getImpl())
}

interface MediaPlayerImpl {
  @DoNotInline
  fun getMediaPlayer(
    context: Context,
  ): MediaPlayer

  fun create(context: Context, uri: Uri): MediaPlayer

  fun create(context: Context, uri: Uri, holder: SurfaceHolder): MediaPlayer

  fun create(
    context: Context,
    uri: Uri,
    holder: SurfaceHolder,
    audioAttributes: AudioAttributes,
    audioSessionId: Int,
  ): MediaPlayer

  fun create(context: Context, @RawRes resid: Int): MediaPlayer

  fun create(
    context: Context,
    @RawRes resid: Int,
    audioAttributes: AudioAttributes,
    audioSessionId: Int,
  ): MediaPlayer
}

private class MediaPlayerBase : MediaPlayerImpl {
  @DoNotInline
  override fun getMediaPlayer(context: Context): MediaPlayer {
    return MediaPlayer()
  }

  override fun create(context: Context, uri: Uri): MediaPlayer {
    return MediaPlayer.create(context, uri)
  }

  override fun create(context: Context, uri: Uri, holder: SurfaceHolder): MediaPlayer {
    return MediaPlayer.create(context, uri, holder)
  }

  override fun create(
    context: Context,
    uri: Uri,
    holder: SurfaceHolder,
    audioAttributes: AudioAttributes,
    audioSessionId: Int,
  ): MediaPlayer {
    return MediaPlayer.create(context, uri, holder, audioAttributes, audioSessionId)
  }

  override fun create(context: Context, @RawRes resid: Int): MediaPlayer {
    return MediaPlayer.create(context, resid)
  }

  override fun create(
    context: Context,
    @RawRes resid: Int,
    audioAttributes: AudioAttributes,
    audioSessionId: Int,
  ): MediaPlayer {
    return MediaPlayer.create(context, resid, audioAttributes, audioSessionId)
  }
}

@RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
private class MediaPlayerApi34 : MediaPlayerImpl {
  @DoNotInline
  override fun getMediaPlayer(context: Context): MediaPlayer {
    return MediaPlayer(context)
  }

  override fun create(context: Context, uri: Uri): MediaPlayer {
    return MediaPlayer.create(context, uri)
  }

  override fun create(context: Context, uri: Uri, holder: SurfaceHolder): MediaPlayer {
    return MediaPlayer.create(context, uri, holder)
  }

  override fun create(
    context: Context,
    uri: Uri,
    holder: SurfaceHolder,
    audioAttributes: AudioAttributes,
    audioSessionId: Int,
  ): MediaPlayer {
    return MediaPlayer.create(context, uri, holder, audioAttributes, audioSessionId)
  }

  override fun create(context: Context, @RawRes resid: Int): MediaPlayer {
    return MediaPlayer.create(context, resid)
  }

  override fun create(
    context: Context,
    @RawRes resid: Int,
    audioAttributes: AudioAttributes,
    audioSessionId: Int,
  ): MediaPlayer {
    return MediaPlayer.create(context, resid, audioAttributes, audioSessionId)
  }
}

class MediaPlayerUtils(
  private val context: Context,
  private val mediaPlayerImpl: MediaPlayerImpl,
) {
  fun create(uri: Uri): MediaPlayer {
    return mediaPlayerImpl.create(context, uri)
  }

  fun create(uri: Uri, holder: SurfaceHolder): MediaPlayer {
    return mediaPlayerImpl.create(context, uri, holder)
  }

  fun create(
    uri: Uri,
    holder: SurfaceHolder,
    audioAttributes: AudioAttributes,
    audioSessionId: Int,
  ): MediaPlayer {
    return mediaPlayerImpl.create(context, uri, holder, audioAttributes, audioSessionId)
  }

  fun create(@RawRes resid: Int): MediaPlayer {
    return mediaPlayerImpl.create(context, resid)
  }

  fun create(
    @RawRes resid: Int,
    audioAttributes: AudioAttributes,
    audioSessionId: Int,
  ): MediaPlayer {
    return mediaPlayerImpl.create(context, resid, audioAttributes, audioSessionId)
  }
}
