/*
 * Copyright 2024 teogor (Teodor Grigor)
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

package dev.teogor.ceres.ui.icons.filled

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

@Suppress("UnusedReceiverParameter")
val Icons.Filled.PermDeviceInformation: ImageVector
  get() {
    if (_permDeviceInformation != null) {
      return _permDeviceInformation!!
    }
    _permDeviceInformation = materialIcon(name = "Filled.PermDeviceInformation") {
      materialPath {
        moveTo(13.0f, 7.0f)
        horizontalLineToRelative(-2.0f)
        verticalLineToRelative(2.0f)
        horizontalLineToRelative(2.0f)
        lineTo(13.0f, 7.0f)
        close()
        moveTo(13.0f, 11.0f)
        horizontalLineToRelative(-2.0f)
        verticalLineToRelative(6.0f)
        horizontalLineToRelative(2.0f)
        verticalLineToRelative(-6.0f)
        close()
        moveTo(17.0f, 1.01f)
        lineTo(7.0f, 1.0f)
        curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
        verticalLineToRelative(18.0f)
        curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
        horizontalLineToRelative(10.0f)
        curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
        lineTo(19.0f, 3.0f)
        curveToRelative(0.0f, -1.1f, -0.9f, -1.99f, -2.0f, -1.99f)
        close()
        moveTo(17.0f, 19.0f)
        lineTo(7.0f, 19.0f)
        lineTo(7.0f, 5.0f)
        horizontalLineToRelative(10.0f)
        verticalLineToRelative(14.0f)
        close()
      }
    }
    return _permDeviceInformation!!
  }

private var _permDeviceInformation: ImageVector? = null
