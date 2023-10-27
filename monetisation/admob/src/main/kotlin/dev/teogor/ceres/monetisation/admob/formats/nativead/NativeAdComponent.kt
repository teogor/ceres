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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.MediaContent
import com.google.android.gms.ads.nativead.NativeAd

data class AdComponent<T>(
  val content: MutableState<T>,
  val composeView: ComposeView,
) {
  fun setValue(value: T) {
    content.value = value
  }
}

/**
 * A composable extension function that simplifies rendering an
 * [AdComponent] by wrapping it in an [AndroidView].
 *
 * @param modifier The modifier for the [AndroidView].
 */
@Composable
fun <T> AdComponent<T>?.RenderContent(
  modifier: Modifier = Modifier,
) {
  this?.let { component ->
    AndroidView(
      factory = { component.composeView },
      modifier = modifier,
    )
  }
}

@Composable
fun <T> createAdComponent(
  initialValue: T,
  ui: @Composable (T) -> Unit,
): AdComponent<T> {
  val adComponent = remember { mutableStateOf(initialValue) }
  val adComponentView = ComposeView(LocalContext.current).apply {
    setContent {
      ui(adComponent.value)
    }
  }
  return AdComponent(
    adComponent,
    adComponentView,
  )
}

@Composable
fun <T> createNullAdComponent(
  ui: @Composable (T) -> Unit,
): AdComponent<T?> {
  val adComponent = remember { mutableStateOf<T?>(null) }
  val adComponentView = ComposeView(LocalContext.current).apply {
    setContent {
      adComponent.value?.let { ui(it) }
    }
  }
  return AdComponent(
    adComponent,
    adComponentView,
  )
}

@Composable
fun createAdvertiserView(
  ui: @Composable (String) -> Unit,
) = createAdComponent(
  initialValue = "",
  ui = ui,
)

@Composable
fun createAdChoicesView(
  ui: @Composable (String) -> Unit,
) = createAdComponent(
  initialValue = "",
  ui = ui,
)

@Composable
fun createHeadlineView(
  ui: @Composable (String) -> Unit,
) = createAdComponent(
  initialValue = "",
  ui = ui,
)

@Composable
fun createBodyView(
  ui: @Composable (String) -> Unit,
) = createAdComponent(
  initialValue = "",
  ui = ui,
)

@Composable
fun createCallToActionView(
  ui: @Composable (String) -> Unit,
) = createAdComponent(
  initialValue = "",
  ui = ui,
)

@Composable
fun createIconView(
  ui: @Composable (NativeAd.Image) -> Unit,
) = createNullAdComponent(
  ui = ui,
)

@Composable
fun createImageView(
  ui: @Composable (String) -> Unit,
) = createAdComponent(
  initialValue = "",
  ui = ui,
)

@Composable
fun createMediaView(
  ui: @Composable (MediaContent) -> Unit,
) = createAdComponent(
  initialValue = MediaContentImpl(),
  ui = ui,
)

@Composable
fun createPriceView(
  ui: @Composable (String) -> Unit,
) = createAdComponent(
  initialValue = "",
  ui = ui,
)

@Composable
fun createStarRatingView(
  ui: @Composable (Double) -> Unit,
) = createAdComponent(
  initialValue = 0.0,
  ui = ui,
)

@Composable
fun createStoreView(
  ui: @Composable (String) -> Unit,
) = createAdComponent(
  initialValue = "",
  ui = ui,
)
