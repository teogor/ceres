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

package dev.teogor.ceres.screen.builder

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import dev.teogor.ceres.screen.builder.compose.AdvancedView
import dev.teogor.ceres.screen.builder.compose.CustomView
import dev.teogor.ceres.screen.builder.compose.HeaderView
import dev.teogor.ceres.screen.builder.compose.SimpleView
import dev.teogor.ceres.screen.builder.model.AdvancedViewBuilder
import dev.teogor.ceres.screen.builder.model.CustomViewBuilder
import dev.teogor.ceres.screen.builder.model.HeaderViewBuilder
import dev.teogor.ceres.screen.builder.model.SimpleViewBuilder
import dev.teogor.ceres.screen.builder.model.ViewBuilder
import dev.teogor.ceres.ui.foundation.clickable
import dev.teogor.ceres.ui.theme.tokens.ColorSchemeKeyTokens

class AboutScreenConfig {
  internal var items: List<ViewBuilder> = emptyList()
}

data class CategoryConfig(
  var modifier: Modifier = Modifier,
  var title: String,
  var titleModifier: Modifier = Modifier,
  var elements: MutableList<ViewBuilder>,
) : ViewBuilder()

data class SubcategoryConfig(
  var title: String?,
  var content: String,
  var singleLine: Boolean = false,
  var modifier: Modifier = Modifier,
) : ViewBuilder()

// endregion

fun BuilderListScope.screenItems(block: MutableList<ViewBuilder>.() -> Unit) {
  val list = mutableListOf<ViewBuilder>()
  list.block()
  AboutScreenConfig().apply {
    this.items = list
  }.let { about ->
    about.items.forEach { item ->
      when (item) {
        is HeaderViewBuilder -> headerItem(item)
        is AdvancedViewBuilder -> advancedView(item)
        is SimpleViewBuilder -> simpleView(item)
        is CustomViewBuilder -> customView(item)
      }
    }
  }
}

@Composable
fun BuilderColumnScope.ScreenItems(block: MutableList<ViewBuilder>.() -> Unit) {
  val list = mutableListOf<ViewBuilder>()
  list.block()
  AboutScreenConfig().apply {
    this.items = list
  }.let { about ->
    about.items.forEach { item ->
      when (item) {
        is HeaderViewBuilder -> HeaderView(item)
        is AdvancedViewBuilder -> AdvancedView(item)
        is SimpleViewBuilder -> SimpleView(item)
        is CustomViewBuilder -> CustomView(item)
      }
    }
  }
}

fun MutableList<ViewBuilder>.customView(
  content: @Composable () -> Unit,
) {
  add(
    CustomViewBuilder(content = content),
  )
}

inline fun MutableList<ViewBuilder>.header(
  crossinline title: () -> String,
) {
  add(
    HeaderViewBuilder(title = title()),
  )
}

inline fun MutableList<ViewBuilder>.advancedView(
  title: String,
  subtitle: String? = null,
  subtitleColor: ColorSchemeKeyTokens? = null,
  icon: ImageVector? = null,
  noinline clickable: (() -> Unit)? = null,
  crossinline block: AdvancedViewBuilder.() -> Unit = {},
) {
  add(
    AdvancedViewBuilder(
      title = title,
      subtitle = subtitle,
      subtitleColor = subtitleColor,
      icon = icon,
      clickable = clickable,
    ).apply(block),
  )
}

fun AdvancedViewBuilder.segmentedButtons(
  options: List<String>,
  selectedOption: Int? = null,
  onOptionSelected: ((Int) -> Unit)? = null,
) {
  this.segmentedOptions = options
  this.segmentedSelectedOption = selectedOption
  this.segmentedOnOptionSelected = onOptionSelected
}

fun AdvancedViewBuilder.switchButton(
  switchToggled: Boolean,
  onSwitchToggled: ((Boolean) -> Unit)? = null,
) {
  this.switchToggled = switchToggled
  this.onSwitchToggled = onSwitchToggled
}

fun AdvancedViewBuilder.customView(
  view: @Composable () -> Unit,
) {
  this.customView = view
}

inline fun CategoryConfig.title(
  modifier: Modifier = Modifier,
  crossinline content: () -> String,
) {
  title = content()
  titleModifier = modifier
}

fun MutableList<ViewBuilder>.simpleView(
  title: String,
  subtitle: String? = null,
  subtitleColor: ColorSchemeKeyTokens? = null,
  icon: ImageVector? = null,
  clickable: (() -> Unit)? = null,
) {
  add(
    SimpleViewBuilder(
      title = title,
      subtitle = subtitle,
      subtitleColor = subtitleColor,
      icon = icon,
      clickable = clickable,
    ),
  )
}

inline fun CategoryConfig.advancedView(
  noinline predicate: (() -> Boolean)? = null,
  modifier: Modifier = Modifier.clickable {},
  crossinline block: SubcategoryConfig.() -> Unit,
) {
  val isVisible = if (predicate != null) {
    predicate()
  } else {
    true
  }
  if (isVisible) {
    val subcategoryConfig = SubcategoryConfig(
      title = "",
      content = "",
      modifier = modifier,
    )
    subcategoryConfig.block()
    elements.add(subcategoryConfig)
  }
}

fun CategoryConfig.customView(
  content: @Composable () -> Unit,
) {
  elements.add(
    CustomViewBuilder(
      content = content,
    ),
  )
}

inline fun SubcategoryConfig.title(crossinline block: () -> String) {
  title = block()
}

inline fun SubcategoryConfig.content(crossinline block: () -> String) {
  content = block()
}

inline fun SubcategoryConfig.singleLine(crossinline block: () -> Boolean) {
  singleLine = block()
}
