package dev.teogor.ceres.screen.core.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import dev.teogor.ceres.navigation.events.TrackScreenViewEvent
import dev.teogor.ceres.screen.core.attachScrollState
import dev.teogor.ceres.screen.core.scope.ScreenListScope
import dev.teogor.ceres.ui.designsystem.CeresBackground
import dev.teogor.ceres.ui.designsystem.scrollbar.LazyColumnScrollbar
import dev.teogor.ceres.ui.designsystem.scrollbar.ScrollbarCustomization

@Composable
fun LayoutWithBottomHeader(
  bottomContent: @Composable ColumnScope.() -> Unit,
  modifier: Modifier = Modifier,
  horizontalAlignment: Alignment.Horizontal = Alignment.Start,
  color: Color = Color.Unspecified,
  tonalElevation: Dp = Dp.Unspecified,
  hasScrollbar: Boolean = true,
  hasScrollbarBackground: Boolean = true,
  screenName: String? = null,
  content: ScreenListScope.() -> Unit,
) {
  val state = rememberLazyListState().attachScrollState()
  CeresBackground(
    color = color,
    tonalElevation = tonalElevation,
  ) {
    Column(
      modifier = Modifier.fillMaxSize(),
    ) {
      Box(
        modifier = Modifier.weight(1f),
      ) {
        LazyColumnScrollbar(
          scrollbarCustomization = ScrollbarCustomization(
            state = state,
          ).copy(
            enabled = hasScrollbar && !(!state.canScrollForward && !state.canScrollBackward),
            hasScrollbarBackground = hasScrollbarBackground,
          ),
        ) {
          LazyColumn(
            state = state,
            modifier = modifier
              .fillMaxSize(),
            horizontalAlignment = horizontalAlignment,
          ) {
            content()
          }
        }
      }
      bottomContent()
    }
  }

  screenName?.let {
    TrackScreenViewEvent(screenName = it)
  }
}
