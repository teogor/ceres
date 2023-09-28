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

package dev.teogor.ceres.ui.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.placeholder.thumbnail.ThumbnailPlugin
import dev.teogor.ceres.ui.designsystem.DigitsImageContent.Image
import dev.teogor.ceres.ui.designsystem.DigitsImageContent.Text
import dev.teogor.ceres.ui.foundation.applyTouchFeedback
import dev.teogor.ceres.ui.foundation.rippleClickable
import dev.teogor.ceres.ui.theme.MaterialTheme
import dev.teogor.ceres.ui.theme.contentColorFor

@Composable
internal fun DigitsImage(
  content: DigitsImageContent,
  modifier: Modifier = Modifier,
  backgroundColor: Color = MaterialTheme.colorScheme.primary,
  onClick: () -> Unit = {},
) {
  Box(
    modifier = modifier
      .clip(CircleShape)
      .background(backgroundColor)
      .rippleClickable(
        rippleColor = MaterialTheme.colorScheme.onPrimary,
      ) {
        applyTouchFeedback()
        onClick()
      },
  ) {
    when (content) {
      is Text -> {
        Text(
          text = content.digits.take(2),
          fontSize = 14.sp,
          color = contentColorFor(backgroundColor),
          modifier = Modifier.align(Alignment.Center),
        )
      }

      is Image -> {
        GlideImage(
          imageModel = { content.imageUrl },
          imageOptions = ImageOptions(
            contentScale = ContentScale.Inside,
            alignment = Alignment.Center,
          ),
          component = rememberImageComponent {
            +ThumbnailPlugin()
          },
          modifier = Modifier
            .padding(end = 10.dp)
            .size(40.dp)
            .clip(CircleShape),
        )
      }
    }
  }
}

internal sealed class DigitsImageContent {
  internal data class Text(
    val digits: String,
  ) : DigitsImageContent()

  internal data class Image(
    val imageUrl: String,
  ) : DigitsImageContent()
}

@Preview
@Composable
fun PreviewDigitsImage() {
  DigitsImage(
    content = Text("ME"),
    modifier = Modifier.size(48.dp),
  )
}
