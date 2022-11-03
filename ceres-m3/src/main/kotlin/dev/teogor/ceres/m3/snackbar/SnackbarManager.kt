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

package dev.teogor.ceres.m3.snackbar

import android.os.Handler
import android.os.Looper
import android.os.Message
import java.lang.ref.WeakReference

/**
 * Manages [Snackbar]s.
 */
internal class SnackbarManager private constructor() {
  private val lock: Any = Any()
  private val handler: Handler
  private var currentSnackbar: SnackbarRecord? = null
  private var nextSnackbar: SnackbarRecord? = null

  init {
    handler = Handler(
      Looper.getMainLooper()
    ) { message: Message ->
      return@Handler if (message.what == MSG_TIMEOUT) {
        handleTimeout(message.obj as SnackbarRecord)
        true
      } else {
        false
      }
    }
  }

  fun show(duration: Int, callback: Callback) {
    synchronized(lock) {
      if (isCurrentSnackbarLocked(callback)) {
        // Means that the callback is already in the queue. We'll just update the duration
        currentSnackbar!!.duration = duration

        // If this is the Snackbar currently being shown, call re-schedule it's
        // timeout
        handler.removeCallbacksAndMessages(currentSnackbar)
        scheduleTimeoutLocked(currentSnackbar!!)
        return
      } else if (isNextSnackbarLocked(callback)) {
        // We'll just update the duration
        nextSnackbar!!.duration = duration
      } else {
        // Else, we need to create a new record and queue it
        nextSnackbar = SnackbarRecord(duration, callback)
      }
      if (currentSnackbar != null &&
        cancelSnackbarLocked(
            currentSnackbar!!,
            com.google.android.material.snackbar.Snackbar.Callback.DISMISS_EVENT_CONSECUTIVE
          )
      ) {
        // If we currently have a Snackbar, try and cancel it and wait in line
        return
      } else {
        // Clear out the current snackbar
        currentSnackbar = null
        // Otherwise, just show it now
        showNextSnackbarLocked()
      }
    }
  }

  fun dismiss(callback: Callback, event: Int) {
    synchronized(lock) {
      if (isCurrentSnackbarLocked(callback)) {
        cancelSnackbarLocked(currentSnackbar!!, event)
      } else if (isNextSnackbarLocked(callback)) {
        cancelSnackbarLocked(nextSnackbar!!, event)
      } else {
        // empty
      }
    }
  }

  /**
   * Should be called when a Snackbar is no longer displayed. This is after any exit animation has
   * finished.
   */
  fun onDismissed(callback: Callback) {
    synchronized(lock) {
      if (isCurrentSnackbarLocked(callback)) {
        // If the callback is from a Snackbar currently show, remove it and show a new one
        currentSnackbar = null
        if (nextSnackbar != null) {
          showNextSnackbarLocked()
        }
      }
    }
  }

  /**
   * Should be called when a Snackbar is being shown. This is after any entrance animation has
   * finished.
   */
  fun onShown(callback: Callback) {
    synchronized(lock) {
      if (isCurrentSnackbarLocked(callback)) {
        scheduleTimeoutLocked(currentSnackbar!!)
      }
    }
  }

  fun pauseTimeout(callback: Callback) {
    synchronized(lock) {
      if (isCurrentSnackbarLocked(callback) && !currentSnackbar!!.paused) {
        currentSnackbar!!.paused = true
        handler.removeCallbacksAndMessages(currentSnackbar)
      }
    }
  }

  fun restoreTimeoutIfPaused(callback: Callback) {
    synchronized(lock) {
      if (isCurrentSnackbarLocked(callback) && currentSnackbar!!.paused) {
        currentSnackbar!!.paused = false
        scheduleTimeoutLocked(currentSnackbar!!)
      }
    }
  }

  fun isCurrent(callback: Callback): Boolean {
    synchronized(lock) { return isCurrentSnackbarLocked(callback) }
  }

  fun isCurrentOrNext(callback: Callback): Boolean {
    synchronized(lock) { return isCurrentSnackbarLocked(callback) || isNextSnackbarLocked(callback) }
  }

  private fun showNextSnackbarLocked() {
    if (nextSnackbar != null) {
      currentSnackbar = nextSnackbar
      nextSnackbar = null
      val callback = currentSnackbar!!.callback.get()
      if (callback != null) {
        callback.show()
      } else {
        // The callback doesn't exist any more, clear out the Snackbar
        currentSnackbar = null
      }
    }
  }

  private fun cancelSnackbarLocked(record: SnackbarRecord, event: Int): Boolean {
    val callback = record.callback.get()
    if (callback != null) {
      // Make sure we remove any timeouts for the SnackbarRecord
      handler.removeCallbacksAndMessages(record)
      callback.dismiss(event)
      return true
    }
    return false
  }

  private fun isCurrentSnackbarLocked(callback: Callback): Boolean {
    return currentSnackbar != null && currentSnackbar!!.isSnackbar(callback)
  }

  private fun isNextSnackbarLocked(callback: Callback): Boolean {
    return nextSnackbar != null && nextSnackbar!!.isSnackbar(callback)
  }

  private fun scheduleTimeoutLocked(r: SnackbarRecord) {
    if (r.duration == com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE) {
      // If we're set to indefinite, we don't want to set a timeout
      return
    }
    var durationMs = LONG_DURATION_MS
    if (r.duration > 0) {
      durationMs = r.duration
    } else if (r.duration == com.google.android.material.snackbar.Snackbar.LENGTH_SHORT) {
      durationMs = SHORT_DURATION_MS
    }
    handler.removeCallbacksAndMessages(r)
    handler.sendMessageDelayed(Message.obtain(handler, MSG_TIMEOUT, r), durationMs.toLong())
  }

  fun handleTimeout(record: SnackbarRecord) {
    synchronized(lock) {
      if (currentSnackbar === record || nextSnackbar === record) {
        cancelSnackbarLocked(record, Snackbar.Callback.DISMISS_EVENT_TIMEOUT)
      }
    }
  }

  interface Callback {
    fun show()
    fun dismiss(event: Int)
  }

  class SnackbarRecord(duration: Int, callback: Callback) {
    @JvmField
    val callback: WeakReference<Callback>
    @JvmField
    var duration: Int
    @JvmField
    var paused = false

    init {
      this.callback = WeakReference(callback)
      this.duration = duration
    }

    fun isSnackbar(callback: Callback?): Boolean {
      return callback != null && this.callback.get() === callback
    }
  }

  companion object {
    const val MSG_TIMEOUT = 0
    private const val SHORT_DURATION_MS = 1500
    private const val LONG_DURATION_MS = 2750
    private var snackbarManager: SnackbarManager? = null
    @JvmStatic
    val instance: SnackbarManager?
      get() {
        if (snackbarManager == null) {
          snackbarManager = SnackbarManager()
        }
        return snackbarManager
      }
  }
}
