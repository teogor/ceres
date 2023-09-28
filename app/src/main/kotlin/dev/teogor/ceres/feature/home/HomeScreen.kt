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

package dev.teogor.ceres.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import dev.teogor.ceres.framework.core.app.BaseActions
import dev.teogor.ceres.framework.core.app.setScreenInfo
import dev.teogor.ceres.framework.core.compositions.LocalNetworkConnectivity
import dev.teogor.ceres.framework.core.screen.floatingButton
import dev.teogor.ceres.framework.core.screen.isStatusBarVisible
import dev.teogor.ceres.framework.core.screen.isVisible
import dev.teogor.ceres.framework.core.screen.showNavBar
import dev.teogor.ceres.framework.core.screen.showSettingsButton
import dev.teogor.ceres.framework.core.screen.toolbarTitle
import dev.teogor.ceres.framework.core.screen.toolbarTokens
import dev.teogor.ceres.monetisation.admob.DemoAdUnitIds
import dev.teogor.ceres.monetisation.admob.formats.nativead.AdLoaderConfig
import dev.teogor.ceres.monetisation.admob.formats.nativead.NativeAd
import dev.teogor.ceres.monetisation.admob.formats.nativead.NativeAdConfig
import dev.teogor.ceres.monetisation.admob.formats.nativead.NativeAdUi
import dev.teogor.ceres.monetisation.admob.formats.nativead.createBodyView
import dev.teogor.ceres.monetisation.admob.formats.nativead.createCallToActionView
import dev.teogor.ceres.monetisation.admob.formats.nativead.createHeadlineView
import dev.teogor.ceres.monetisation.admob.formats.nativead.createIconView
import dev.teogor.ceres.monetisation.messaging.ConsentManager
import dev.teogor.ceres.monetisation.messaging.ConsentResult
import dev.teogor.ceres.navigation.core.utilities.toScreenName
import dev.teogor.ceres.screen.builder.compose.HeaderView
import dev.teogor.ceres.screen.builder.compose.SimpleView
import dev.teogor.ceres.screen.builder.model.HeaderViewBuilder
import dev.teogor.ceres.screen.builder.model.SimpleViewBuilder
import dev.teogor.ceres.screen.core.layout.ColumnLayoutBase
import dev.teogor.ceres.ui.designsystem.Text
import dev.teogor.ceres.ui.theme.MaterialTheme

// todo better way to configure this. perhaps use kotlin builder syntax
@Composable
internal fun HomeRoute(
  baseActions: BaseActions,
  homeVM: HomeViewModel = hiltViewModel(),
) {
  val state by remember { ConsentManager.state }
  val canRequestAds: Boolean = remember(state) {
    when (val consentResult = state) {
      is ConsentResult.ConsentFormAcquired -> consentResult.canRequestAds
      is ConsentResult.ConsentFormDismissed -> consentResult.canRequestAds
      else -> false
    }
  }

  baseActions.setScreenInfo {
    showNavBar {
      true
    }

    isStatusBarVisible {
      true
    }

    toolbarTokens {
      isVisible {
        true
      }

      toolbarTitle {
        "Home"
      }

      showSettingsButton {
        true
      }
    }

    floatingButton {
      isVisible {
        false
      }
    }
  }

  val networkConnectivity = LocalNetworkConnectivity.current

  if (canRequestAds) {
    HomeScreen(
      homeVM,
      isOffline = networkConnectivity.isOffline,
    )
  } else {
    ConsentManager.loadAndShowConsentFormIfRequired()
  }
}

@Composable
private fun HomeScreen(
  homeVM: HomeViewModel,
  isOffline: Boolean,
) = ColumnLayoutBase(
  hasScrollbarBackground = false,
  screenName = HomeScreenConfig.toScreenName(),
) {
  HeaderView(HeaderViewBuilder("Ad Settings"))

  SimpleView(
    SimpleViewBuilder(
      title = "Reset Advertising Choices",
      subtitle = "Reset your advertising choices to manage your options.",
      clickable = {
        ConsentManager.resetConsent()
      },
    ),
  )

  HeaderView(HeaderViewBuilder("Ads Demo"))

  // customView {
  //   dev.teogor.ceres.monetisation.admob.beta.formats.ui.AdmobBanner(
  //     modifier = Modifier.fillMaxWidth(),
  //     bannerAd = homeVM.homeBannerAd,
  //   )
  // }

  SimpleView(
    SimpleViewBuilder(
      title = "Show Interstitial" + if (isOffline) " (Off)" else "",
      clickable = {
        homeVM.homeInterstitialAd.show()
      },
    ),
  )

  SimpleView(
    SimpleViewBuilder(
      title = "Show Rewarded Interstitial" + if (isOffline) " (Off)" else "",
      clickable = {
        homeVM.homeRewardedInterstitialAd.show()
      },
    ),
  )

  SimpleView(
    SimpleViewBuilder(
      title = "Show Rewarded" + if (isOffline) " (Off)" else "",
      clickable = {
        homeVM.homeRewardedAd.show()
      },
    ),
  )

  val adId = DemoAdUnitIds.NATIVE
  val nativeAd by remember {
    homeVM.nativeAd
  }
  if (!isOffline) {
    val nativeAdConfig = nativeAdConfig()

    NativeAd(
      modifier = Modifier
        .padding(horizontal = 10.dp)
        .background(
          color = MaterialTheme.colorScheme.primaryContainer,
          shape = RoundedCornerShape(20.dp),
        )
        .padding(horizontal = 6.dp, vertical = 10.dp),
      nativeAdConfig = nativeAdConfig,
      adContent = {
        NativeAdUi(nativeAdConfig)
      },
      nativeAd = nativeAd,
      config = AdLoaderConfig(adId),
      refreshIntervalMillis = 30000L,
      onAdLoaded = {
        homeVM.setNativeAd(it)
      },
    )
  }
}

@Composable
fun nativeAdConfig() = NativeAdConfig.Builder()
  .headlineView(
    createHeadlineView {
      Text(
        text = it,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        fontSize = 18.sp,
      )
    },
  )
  .bodyView(
    createBodyView {
      Text(
        text = it,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        fontSize = 10.sp,
        maxLines = 1,
      )
    },
  )
  .callToActionView(
    createCallToActionView {
      Text(
        text = it,
        color = MaterialTheme.colorScheme.onPrimary,
        fontSize = 12.sp,
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
        imageModel = { it.uri }, // loading a network image using an URL.
        imageOptions = ImageOptions(
          contentScale = ContentScale.Crop,
          alignment = Alignment.Center,
        ),
      )
    },
  )
  .build()
