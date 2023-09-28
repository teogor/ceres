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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import dev.teogor.ceres.ui.theme.MaterialTheme

@Composable
fun NativeAdUi(
  nativeAdConfig: NativeAdConfig,
) {
  val advertiserView = nativeAdConfig.advertiserView
  val adChoicesView = nativeAdConfig.adChoicesView
  val headlineView = nativeAdConfig.headlineView
  val bodyView = nativeAdConfig.bodyView
  val callToActionView = nativeAdConfig.callToActionView
  val iconView = nativeAdConfig.iconView
  val imageView = nativeAdConfig.imageView
  val mediaView = nativeAdConfig.mediaView
  val priceView = nativeAdConfig.priceView
  val starRatingView = nativeAdConfig.starRatingView
  val storeView = nativeAdConfig.storeView

  Column(
    modifier = Modifier.fillMaxWidth(),
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
    ) {
      iconView?.let { iconView ->
        AndroidView(
          factory = {
            iconView.composeView
          },
        )
      }
      Column(
        modifier = Modifier.padding(start = 6.dp),
      ) {
        headlineView?.let { headlineView ->
          AndroidView(
            factory = {
              headlineView.composeView
            },
          )
        }
        bodyView?.let { bodyView ->
          AndroidView(
            factory = {
              bodyView.composeView
            },
          )
        }
      }
    }
    callToActionView?.let { callToActionView ->
      AndroidView(
        factory = {
          callToActionView.composeView
        },
        modifier = Modifier
          .align(Alignment.CenterHorizontally)
          .background(
            color = MaterialTheme.colorScheme.primary,
            shape = ButtonDefaults.shape,
          )
          .padding(horizontal = 10.dp, vertical = 6.dp),
      )
    }
  }
}
