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

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.zeoflow.startup.ktx.ApplicationInitializer
import dev.teogor.ceres.extensions.validStringRes

@BindingAdapterClass
class MaterialBinding {

  companion object {
    @BindingAdapter("errorMessage")
    @JvmStatic
    fun setErrorMessage(view: TextInputLayout, @StringRes errorMessageRes: Int) {
      errorMessageRes.validStringRes {
        view.error = ApplicationInitializer.context.getString(this)
      }
    }

    @BindingAdapter("icon")
    @JvmStatic
    fun buttonIcon(view: MaterialButton, @DrawableRes iconResourceId: Int) {
      view.setIconResource(iconResourceId)
    }
  }
}
