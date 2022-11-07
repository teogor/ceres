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

package dev.teogor.ceres.bindings

import android.view.View
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.zeoflow.startup.ktx.ApplicationInitializer

@BindingAdapterClass
class ViewBinding {

  companion object {
    @BindingAdapter("android:contentDescription")
    @JvmStatic
    fun viewContent(view: View, @StringRes textResId: Int) {
      var contentDescription: String? = null
      if (textResId != 0) {
        contentDescription = ApplicationInitializer.context.getString(textResId)
      }
      view.contentDescription = contentDescription
    }

    @BindingAdapter("show")
    @JvmStatic
    fun showView(view: View, isVisible: Boolean) {
      if (isVisible) {
        view.visibility = View.VISIBLE
      } else {
        view.visibility = View.GONE
      }
    }
  }
}
