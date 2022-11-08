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

package dev.teogor.ceres.components

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView
import dev.teogor.ceres.core.logger.Logger

class ScrollableFragmentRoot constructor(
  context: Context,
  attrs: AttributeSet
) : ScrollView(context, attrs), Logger {

  private var onScroll: OnScroll? = null

  fun scrollToTop() {
    smoothScrollTo(0, 0)
  }

  var topStableEvent: Event = Event.NotSet
  var bottomStableEvent: Event = Event.NotSet

  override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
    super.onScrollChanged(l, t, oldl, oldt)

    requestEvent()
  }

  fun setEventListener(onScroll: OnScroll) {
    this.onScroll = onScroll
    requestEvent()
  }

  private fun requestEvent() {
    val topEvent = if (canScrollVertically(-1)) {
      Event.TopLeft
    } else {
      Event.TopReached
    }
    if (topStableEvent != topEvent) {
      onScroll?.onScrollEvent(topEvent)
      topStableEvent = topEvent
    }
    val bottomEvent = if (canScrollVertically(1)) {
      Event.BottomLeft
    } else {
      Event.BottomReached
    }
    if (bottomStableEvent != bottomEvent) {
      onScroll?.onScrollEvent(bottomEvent)
      bottomStableEvent = bottomEvent
    }
  }

  interface OnScroll {
    fun onScrollEvent(type: Event) = Unit
  }

  enum class Event {
    NotSet,
    TopReached,
    TopLeft,
    BottomReached,
    BottomLeft
  }
}

inline fun ScrollableFragmentRoot.scrollEvent(
  crossinline continuation: (ScrollableFragmentRoot.Event) -> Unit
) {
  setEventListener(object : ScrollableFragmentRoot.OnScroll {
    override fun onScrollEvent(type: ScrollableFragmentRoot.Event) {
      continuation(type)
    }
  })
}
