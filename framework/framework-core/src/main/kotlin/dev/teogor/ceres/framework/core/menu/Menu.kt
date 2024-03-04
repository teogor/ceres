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

package dev.teogor.ceres.framework.core.menu

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import dev.teogor.ceres.ui.designsystem.HorizontalDivider

typealias MenuScope = LazyListScope

open class MenuItem

class MenuItemContent(
  val content: String,
  val icon: ImageVector,
  val description: String? = null,
  val clickable: () -> Unit = {},
) : MenuItem()

class MenuDivider : MenuItem()

class CustomMenuItem(
  val content: @Composable () -> Unit,
  val clickable: () -> Unit = {},
) : MenuItem()

class MenuFooter(
  val licenseHolder: String,
  val modifier: Modifier = Modifier,
) : MenuItem()

class Menu {
  internal var topItems: List<MenuItem> = emptyList()
  internal var items: List<MenuItem> = emptyList()
}

fun MenuScope.menu(
  block: Menu.() -> Unit,
) {
  Menu().apply(block).let { menu ->
    items(
      menu.topItems.size,
    ) { index ->
      when (val item = menu.topItems[index]) {
        is MenuItemContent -> {
          MenuTopSurface(
            topRadius = index == 0,
            bottomRadius = index == menu.topItems.size - 1,
            clickable = item.clickable,
          ) {
            MenuItem(
              content = item.content,
              icon = item.icon,
            )
          }
        }

        is MenuDivider -> {
          HorizontalDivider(
            thickness = 2.dp,
            color = Color.Transparent,
          )
        }

        is CustomMenuItem -> {
          MenuTopSurface(
            topRadius = index == 0,
            bottomRadius = index == menu.topItems.size - 1,
            clickable = item.clickable,
          ) {
            item.content()
          }
        }
      }
    }
    item {
      Spacer(modifier = Modifier.height(10.dp))
    }
    items(
      menu.items.size,
    ) { index ->
      when (val item = menu.items[index]) {
        is MenuItemContent -> {
          MenuItem(
            content = item.content,
            icon = item.icon,
            clickable = item.clickable,
          )
        }

        is MenuDivider -> {
          HorizontalDivider()
        }

        is MenuFooter -> {
          MenuFooterItem(
            licenseHolder = item.licenseHolder,
            modifier = item.modifier,
          )
        }

        is CustomMenuItem -> {
          item.content()
        }
      }
    }
  }
}

fun Menu.menuTop(block: MutableList<MenuItem>.() -> Unit) {
  val list = mutableListOf<MenuItem>()
  list.block()
  this.topItems = list
}

fun Menu.menuContent(block: MutableList<MenuItem>.() -> Unit) {
  val list = mutableListOf<MenuItem>()
  list.block()
  this.items = list
}

fun MutableList<MenuItem>.menuItem(
  content: String,
  icon: ImageVector,
  description: String? = null,
  clickable: () -> Unit = {},
) {
  add(
    MenuItemContent(
      content = content,
      icon = icon,
      description = description,
      clickable = clickable,
    ),
  )
}

fun MutableList<MenuItem>.menuDivider() {
  add(
    MenuDivider(),
  )
}

fun MutableList<MenuItem>.menuFooter(
  licenseHolder: String,
  modifier: Modifier = Modifier,
) {
  add(
    MenuFooter(
      licenseHolder = licenseHolder,
      modifier = modifier,
    ),
  )
}

fun MutableList<MenuItem>.menuItem(
  clickable: () -> Unit = {},
  content: @Composable () -> Unit,
) {
  add(
    CustomMenuItem(
      content = content,
      clickable = clickable,
    ),
  )
}
