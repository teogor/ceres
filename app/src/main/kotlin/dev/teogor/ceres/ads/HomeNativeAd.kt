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

package dev.teogor.ceres.ads

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.ads.nativead.NativeAd
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.teogor.ceres.monetisation.admob.TestAdUnitIds
import dev.teogor.ceres.monetisation.admob.annotations.AdProperty
import dev.teogor.ceres.monetisation.admob.formats.nativead.NativeAd
import dev.teogor.ceres.monetisation.admob.formats.nativead.NativeAdConfig
import dev.teogor.ceres.monetisation.admob.formats.nativead.NativeAdContainer
import dev.teogor.ceres.monetisation.admob.formats.nativead.NativeAdData
import dev.teogor.ceres.monetisation.admob.formats.nativead.NativeAdManager
import dev.teogor.ceres.monetisation.admob.formats.nativead.NativeAdViewModel
import dev.teogor.ceres.monetisation.admob.formats.nativead.RenderContent
import dev.teogor.ceres.monetisation.admob.formats.nativead.createBodyView
import dev.teogor.ceres.monetisation.admob.formats.nativead.createCallToActionView
import dev.teogor.ceres.monetisation.admob.formats.nativead.createHeadlineView
import dev.teogor.ceres.monetisation.admob.formats.nativead.createIconView
import dev.teogor.ceres.monetisation.admob.formats.nativead.createStarRatingView
import dev.teogor.ceres.monetisation.admob.formats.nativead.defaultAdLoaderConfig
import dev.teogor.ceres.monetisation.admob.formats.nativead.nativeAdBackground
import dev.teogor.ceres.ui.designsystem.RatingBar
import dev.teogor.ceres.ui.designsystem.Text
import dev.teogor.ceres.ui.designsystem.core.ColorUtils.blend
import dev.teogor.ceres.ui.theme.MaterialTheme
import javax.inject.Inject
import javax.inject.Singleton

@AdProperty
@Singleton
data class HomeNativeAdData @Inject constructor(
  override val nativeAd: MutableState<NativeAd?>,
) : NativeAdData(nativeAd)

@HiltViewModel
class HomeNativeAdVM @Inject constructor(
  homeNativeAdData: HomeNativeAdData,
) : NativeAdViewModel(homeNativeAdData)

class HomeNativeAdBeta : NativeAdManager() {
  @Composable
  override fun config() = NativeAdConfig.Builder()
    .headlineView(
      createHeadlineView {
        Text(
          text = if (it.length > 25) it.take(25) else it,
          color = MaterialTheme.colorScheme.onPrimaryContainer,
          fontSize = 18.sp,
          lineHeight = 20.sp,
        )
      },
    )
    .bodyView(
      createBodyView {
        Text(
          text = if (it.length > 90) it.take(90) else it,
          color = MaterialTheme.colorScheme.onPrimaryContainer,
          fontSize = 10.sp,
          lineHeight = 12.sp,
        )
      },
    )
    .starRatingView(
      createStarRatingView {
        RatingBar(
          rating = it,
          starSize = 18.dp,
        )
      },
    )
    .callToActionView(
      createCallToActionView {
        Text(
          text = it,
          color = MaterialTheme.colorScheme.onPrimary,
          fontSize = 12.sp,
          modifier = Modifier
            .padding(top = 4.dp)
            .background(
              color = MaterialTheme.colorScheme.primary,
              shape = ButtonDefaults.shape,
            )
            .padding(horizontal = 24.dp, vertical = 8.dp),
        )
      },
    )
    .iconView(
      createIconView {
        GlideImage(
          modifier = Modifier
            .size(50.dp)
            .background(
              color = MaterialTheme.colorScheme.background.copy(alpha = .2f),
              shape = RoundedCornerShape(10.dp),
            )
            .clip(RoundedCornerShape(10.dp))
            .padding(6.dp),
          imageModel = { it.uri },
          imageOptions = ImageOptions(
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
          ),
        )
      },
    )
    .build()

  @Composable
  override fun Display() {
    IsOnline {
      val adId = TestAdUnitIds.NATIVE_ADVANCED
      var isAdFillEmpty by remember { mutableStateOf(true) }
      NativeAd<HomeNativeAdVM>(
        modifier = Modifier
          .padding(
            horizontal = 10.dp,
            vertical = 10.dp,
          )
          .fillMaxWidth(),
        viewModel = hiltViewModel(),
        nativeAdConfig = config(),
        adContent = { it.UI(isAdFillEmpty) },
        config = defaultAdLoaderConfig(adId),
        refreshIntervalMillis = 30_000L,
        onAdFillStatusChange = { isAdFillEmpty = it },
        onRetrieveBackground = {
          nativeAdBackground(
            color = MaterialTheme.colorScheme.tertiaryContainer,
            adChoicesPlacement = it,
            cornerSize = 20.dp,
            shadowElevation = 2.dp,
          )
        },
      )
    }
  }

  @Composable
  override fun NativeAdConfig.UI(
    isAdFillEmpty: Boolean,
  ) {
    NativeAdContainer(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 10.dp, horizontal = 6.dp),
      isAdFillEmpty = isAdFillEmpty,
      showDefaultAdText = true,
      adTextAlignment = Alignment.BottomEnd,
      loadingOverlay = {
        LinearProgressIndicator(
          modifier = Modifier
            .align(Alignment.Center)
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(50)),
          color = MaterialTheme.colorScheme.primary,
          trackColor = MaterialTheme.colorScheme.onTertiaryContainer.blend(
            MaterialTheme.colorScheme.background,
            fraction = .6f,
          ),
        )
      },
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
      ) {
        iconView.RenderContent(
          modifier = Modifier.align(Alignment.Top),
        )
        Column(
          modifier = Modifier.padding(start = 6.dp),
        ) {
          headlineView.RenderContent()
          starRatingView.RenderContent()
          bodyView.RenderContent()
        }
      }
      callToActionView.RenderContent(
        modifier = Modifier.align(Alignment.CenterHorizontally),
      )
    }
  }
}
