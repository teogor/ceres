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
val Icons.Filled.DomainVerification: ImageVector
  get() {
    if (_domainVerification != null) {
      return _domainVerification!!
    }
    _domainVerification = materialIcon(name = "Filled.DomainVerification") {
      materialPath {
        moveTo(16.6f, 10.88f)
        lineToRelative(-1.42f, -1.42f)
        lineToRelative(-4.24f, 4.25f)
        lineToRelative(-2.12f, -2.13f)
        lineToRelative(-1.42f, 1.42f)
        lineToRelative(3.54f, 3.54f)
        close()
      }
      materialPath {
        moveTo(19.0f, 4.0f)
        horizontalLineTo(5.0f)
        curveTo(3.89f, 4.0f, 3.0f, 4.9f, 3.0f, 6.0f)
        verticalLineToRelative(12.0f)
        curveToRelative(0.0f, 1.1f, 0.89f, 2.0f, 2.0f, 2.0f)
        horizontalLineToRelative(14.0f)
        curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
        verticalLineTo(6.0f)
        curveTo(21.0f, 4.9f, 20.11f, 4.0f, 19.0f, 4.0f)
        close()
        moveTo(19.0f, 18.0f)
        horizontalLineTo(5.0f)
        verticalLineTo(8.0f)
        horizontalLineToRelative(14.0f)
        verticalLineTo(18.0f)
        close()
      }
    }
    return _domainVerification!!
  }

private var _domainVerification: ImageVector? = null
