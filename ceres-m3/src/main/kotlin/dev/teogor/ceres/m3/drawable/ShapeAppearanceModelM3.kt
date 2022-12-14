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

package dev.teogor.ceres.m3.drawable

import com.google.android.material.shape.ShapeAppearanceModel

@Deprecated(message = "In favour of Beta.ShapeAppearanceModelM3")
class ShapeAppearanceModelM3 : ShapeAppearanceModel() {

  /** Builder to create instances of [ShapeAppearanceModelM3]s.  */
  data class Builder(
    private var corners: Float
  ) {

    fun topCorners(colorM3: Float) = apply { this.corners = colorM3 }
  }
}
