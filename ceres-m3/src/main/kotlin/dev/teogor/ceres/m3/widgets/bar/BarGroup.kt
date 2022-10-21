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

package dev.teogor.ceres.m3.widgets.bar

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import dev.teogor.ceres.core.logger.Logger
import dev.teogor.ceres.m3.R

class BarGroup constructor(
  context: Context,
  attrs: AttributeSet
) : View(context, attrs), Logger {

  private var mIds: IntArray
  private var mReferenceIds: String
  private var mCount = 0
  private var mMap = HashMap<Int, String>()

  init {
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BarAnimator,
      0,
      0
    ).apply {
      try {
        mReferenceIds = getString(
          R.styleable.BarAnimator_constraint_referenced_ids
        ).toString()
        val count = mReferenceIds.count { ch -> ch == ',' } + 1
        mIds = IntArray(count)
        setIds(mReferenceIds)
      } finally {
        recycle()
      }
    }
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    setIds(mReferenceIds)
    prepareBars()
  }

  private fun setIds(idList: String) {
    mReferenceIds = idList
    var begin = 0
    mCount = 0
    while (true) {
      val end = idList.indexOf(',', begin)
      if (end == -1) {
        addID(idList.substring(begin))
        break
      }
      addID(idList.substring(begin, end))
      begin = end + 1
    }
  }

  private fun addID(idString: String) {
    var idStringCopy: String = idString
    if (idStringCopy.isEmpty()) {
      return
    }
    if (context == null) {
      return
    }
    idStringCopy = idStringCopy.trim { it <= ' ' }
    var parent: ConstraintLayout? = null
    if (getParent() is ConstraintLayout) {
      parent = getParent() as ConstraintLayout
    }
    val rscId: Int = findId(idStringCopy)
    if (rscId != 0) {
      mMap[rscId] =
        idStringCopy // let's remember the idString used, as we may need it for dynamic modules
      addRscID(rscId)
    } else {
      log("Could not find id of \"$idStringCopy\"")
    }
  }

  @SuppressLint("DiscouragedApi")
  private fun findId(referenceId: String): Int {
    var parent: ConstraintLayout? = null
    if (getParent() is ConstraintLayout) {
      parent = getParent() as ConstraintLayout
    }
    var rscId = 0

    // First, if we are in design mode let's get the cached information
    if (isInEditMode && parent != null) {
      val value = parent.getDesignInformation(0, referenceId)
      if (value is Int) {
        rscId = value
      }
    }

    // ... if not, let's check our siblings
    if (rscId == 0 && parent != null) {
      // TODO: cache this in ConstraintLayout
      rscId = findId(parent, referenceId)
    }
    if (rscId == 0) {
      try {
        val res: Class<*> = R.id::class.java
        val field = res.getField(referenceId)
        rscId = field.getInt(null)
      } catch (e: Exception) {
        // Do nothing
      }
    }
    if (rscId == 0) {
      // this will first try to parse the string id as a number (!) in ResourcesImpl, so
      // let's try that last...
      rscId = context.resources.getIdentifier(
        referenceId,
        "id",
        context.packageName
      )
    }
    return rscId
  }

  private fun findId(container: ConstraintLayout?, idString: String?): Int {
    if (idString == null || container == null) {
      return 0
    }
    val resources: Resources = context.resources ?: return 0
    val count = container.childCount
    for (j in 0 until count) {
      val child = container.getChildAt(j)
      if (child.id != -1) {
        var res: String? = null
        try {
          res = resources.getResourceEntryName(child.id)
        } catch (e: Resources.NotFoundException) {
          // nothing
        }
        if (idString == res) {
          return child.id
        }
      }
    }
    return 0
  }

  private fun addRscID(id: Int) {
    if (id == getId()) {
      return
    }
    if (mCount + 1 > mIds.size) {
      mIds = mIds.copyOf(mIds.size * 2)
    }
    mIds[mCount] = id
    mCount++
  }

  private fun prepareBars() {
    getToolBar().setStatusBar(getStatusBar())
  }

  private fun getToolBar(): ToolBar {
    val container = parent as ConstraintLayout
    return mIds.filter { id ->
      val view = container.getViewById(id)
      view is ToolBar
    }.map { id -> container.getViewById(id) as ToolBar }.first()
  }

  private fun getStatusBar(): StatusBar {
    val container = parent as ConstraintLayout
    return mIds.filter { id ->
      val view = container.getViewById(id)
      view is StatusBar
    }.map { id -> container.getViewById(id) as StatusBar }.first()
  }
}
