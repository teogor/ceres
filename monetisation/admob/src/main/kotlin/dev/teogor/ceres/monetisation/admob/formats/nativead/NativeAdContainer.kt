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

package dev.teogor.ceres.monetisation.admob.formats.nativead

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.teogor.ceres.ui.designsystem.Text
import dev.teogor.ceres.ui.theme.MaterialTheme

/**
 * A composable container for displaying native ads with optional
 * loading overlay and custom text.
 *
 * @param modifier The modifier to be applied to the container.
 * @param isAdFillEmpty Flag indicating whether the ad content is empty.
 * @param loadingOverlay Composable lambda for displaying a loading overlay.
 * @param showDefaultAdText Flag to determine if the default "AD" text
 *                          should be displayed.
 * @param adTextAlignment Alignment for positioning the "AD" text within
 *                        the container.
 * @param content The content of the native ad.
 */
@Composable
fun NativeAdContainer(
  modifier: Modifier = Modifier,
  isAdFillEmpty: Boolean = false,
  loadingOverlay: @Composable BoxScope.() -> Unit = {},
  showDefaultAdText: Boolean = false,
  adTextAlignment: Alignment = Alignment.BottomEnd,
  content: @Composable ColumnScope.() -> Unit,
) {
  Box {
    Column(
      modifier = Modifier
        .alpha(if (isAdFillEmpty) 0f else 1f)
        .then(modifier),
      content = content,
    )

    if (showDefaultAdText && !isAdFillEmpty) {
      Text(
        text = "AD",
        fontSize = 10.sp,
        lineHeight = 10.sp,
        color = MaterialTheme.colorScheme.onTertiary,
        modifier = Modifier
          .align(adTextAlignment)
          .background(
            color = MaterialTheme.colorScheme.tertiary,
            shape = CircleShape,
          )
          .padding(horizontal = 8.dp, vertical = 8.dp)
          .padding(end = 2.dp, bottom = 2.dp),
      )
    }

    if (isAdFillEmpty) {
      loadingOverlay()
    }
  }
}
