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

package dev.teogor.ceres.screen.ui.about.libraries

import android.content.Context
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.util.withContext
import dev.teogor.ceres.screen.core.layout.LazyColumnLayoutBase
import dev.teogor.ceres.screen.core.scope.ScreenListScope
import dev.teogor.ceres.screen.ui.about.aboutLibrariesNavigationRoute
import dev.teogor.ceres.screen.ui.about.libraries.util.StableLibrary
import dev.teogor.ceres.screen.ui.about.libraries.util.author
import dev.teogor.ceres.screen.ui.about.libraries.util.htmlReadyLicenseContent
import dev.teogor.ceres.screen.ui.about.libraries.util.stable
import dev.teogor.ceres.ui.compose.ToolbarBackground
import dev.teogor.ceres.ui.designsystem.AlertDialog
import dev.teogor.ceres.ui.designsystem.Button
import dev.teogor.ceres.ui.designsystem.Text
import dev.teogor.ceres.ui.foundation.clickable
import dev.teogor.ceres.ui.theme.MaterialTheme
import dev.teogor.ceres.ui.theme.Typography
import dev.teogor.ceres.ui.theme.contentColorFor
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Displays all provided libraries in a simple list.
 */
@Composable
fun LibrariesContainer(
  modifier: Modifier = Modifier,
  contentPadding: PaddingValues = PaddingValues(0.dp),
  librariesBlock: (Context) -> Libs = { context ->
    Libs.Builder().withContext(context).build()
  },
  showAuthor: Boolean = true,
  showVersion: Boolean = true,
  showLicenseBadges: Boolean = true,
  colors: LibraryColors = LibraryDefaults.libraryColors(),
  padding: LibraryPadding = LibraryDefaults.libraryPadding(),
  itemContentPadding: PaddingValues = LibraryDefaults.ContentPadding,
  itemSpacing: Dp = LibraryDefaults.LibraryItemSpacing,
  header: (LazyListScope.() -> Unit)? = null,
  onLibraryClick: ((Library) -> Unit)? = null,
) {
  val context = LocalContext.current
  val uriHandler = LocalUriHandler.current

  val libraries = produceState<Libs?>(null) {
    value = withContext(Dispatchers.IO) {
      librariesBlock(context)
    }
  }

  val libs = libraries.value?.libraries?.stable
  if (libs != null) {
    val openDialog = remember { mutableStateOf<Library?>(null) }
    Libraries(
      libraries = libs,
      modifier = modifier,
      contentPadding = contentPadding,
      showAuthor = showAuthor,
      showVersion = showVersion,
      showLicenseBadges = showLicenseBadges,
      colors = colors,
      padding = padding,
      itemContentPadding = itemContentPadding,
      itemSpacing = itemSpacing,
      header = header,
      onLibraryClick = { library ->
        val license = library.licenses.firstOrNull()
        if (onLibraryClick != null) {
          onLibraryClick(library)
        } else if (!license?.htmlReadyLicenseContent.isNullOrBlank()) {
          openDialog.value = library
        } else if (!license?.url.isNullOrBlank()) {
          license?.url?.also { uriHandler.openUri(it) }
        }
      },
    )

    val library = openDialog.value
    if (library != null) {
      LicenseDialog(library = library.stable, colors) {
        openDialog.value = null
      }
    }
  }
}

@Composable
fun LicenseDialog(
  library: StableLibrary,
  colors: LibraryColors = LibraryDefaults.libraryColors(),
  onDismiss: () -> Unit,
) {
  val scrollState = rememberScrollState()
  AlertDialog(
    modifier = Modifier.padding(vertical = 30.dp),
    onDismissRequest = onDismiss,
    confirmButton = {
      Button(onClick = onDismiss) {
        Text(stringResource(id = android.R.string.ok))
      }
    },
    title = {
      Text(
        text = "License",
      )
    },
    text = {
      Column(
        modifier = Modifier.verticalScroll(scrollState),
      ) {
        HtmlText(
          html = library.library.licenses.firstOrNull()?.htmlReadyLicenseContent.orEmpty(),
          color = colors.contentColor,
        )
      }
    },
  )
}

@Composable
fun HtmlText(
  html: String,
  modifier: Modifier = Modifier,
  color: Color = LibraryDefaults.libraryColors().contentColor,
) {
  AndroidView(
    modifier = modifier,
    factory = { context ->
      TextView(context).apply {
        setTextColor(color.toArgb())
      }
    },
    update = { it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT) },
  )
}

/**
 * Displays all provided libraries in a simple list.
 */
@Composable
fun Libraries(
  libraries: ImmutableList<StableLibrary>,
  modifier: Modifier = Modifier,
  contentPadding: PaddingValues = PaddingValues(0.dp),
  showAuthor: Boolean = true,
  showVersion: Boolean = true,
  showLicenseBadges: Boolean = true,
  colors: LibraryColors = LibraryDefaults.libraryColors(),
  padding: LibraryPadding = LibraryDefaults.libraryPadding(),
  itemContentPadding: PaddingValues = LibraryDefaults.ContentPadding,
  itemSpacing: Dp = LibraryDefaults.LibraryItemSpacing,
  header: (LazyListScope.() -> Unit)? = null,
  onLibraryClick: ((Library) -> Unit)? = null,
) {
  // todo locals for ceres local providers... view CompositionLocals.kt
  val uriHandler = LocalUriHandler.current

  var authorTag by remember {
    mutableStateOf("")
  }
  val authorGroups = setOf(
    Pair("Google Team", listOf("Google, Inc.", "Google LLC", "Google")),
  )

  LazyColumnLayoutBase(
    screenName = aboutLibrariesNavigationRoute,
    hasScrollbarBackground = false,
    indicatorContent = { index ->
      if (index < libraries.size) {
        libraries[index].library.name.take(2).uppercase()
      } else {
        ""
      }
    },
    verticalArrangement = Arrangement.spacedBy(itemSpacing),
    contentPadding = contentPadding,
    header = {
      ToolbarBackground(
        paddingBottom = 6.dp,
      ) {
        AuthorTags(
          libraries,
          selectedAuthorTag = authorTag,
          authorGroups = authorGroups,
        ) { tag ->
          authorTag = tag
        }
      }
    },
  ) {
    header?.invoke(this)
    libraryItems(
      libraries = if (authorTag.isEmpty()) {
        libraries
      } else {
        libraries.filter { library ->
          val author = library.library.author
          val isInGroup = authorGroups.any { (group, variants) ->
            if (group == authorTag) {
              author in variants || author == group
            } else {
              false
            }
          }

          if (isInGroup) {
            true
          } else if (authorTag == "No Author") {
            author == ""
          } else {
            author == authorTag
          }
        }.toImmutableList()
      },
      showAuthor,
      showVersion,
      showLicenseBadges,
      colors,
      padding,
      itemContentPadding,
    ) { library ->
      val license = library.licenses.firstOrNull()
      if (onLibraryClick != null) {
        onLibraryClick.invoke(library)
      } else if (!license?.url.isNullOrBlank()) {
        license?.url?.also { uriHandler.openUri(it) }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal inline fun AuthorTags(
  libraries: ImmutableList<StableLibrary>,
  selectedAuthorTag: String,
  authorGroups: Set<Pair<String, List<String>>>,
  crossinline onAuthorTagClick: (String) -> Unit,
) {
  val authorCounts = with(mutableMapOf<String, Int>()) {
    libraries.forEach { lib ->
      lib.library.author.let { author ->
        if (author.isNotEmpty()) {
          val mappedAuthor = authorGroups.firstOrNull { (_, variants) ->
            author in variants
          }?.first ?: author
          this[mappedAuthor] = getOrDefault(mappedAuthor, 0) + 1
        } else {
          this["No Author"] = getOrDefault("No Author", 0) + 1
        }
      }
    }
    toSortedMap(compareBy<String> { it != "No Author" }.thenBy { it })
  }

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .horizontalScroll(rememberScrollState())
      .padding(horizontal = 10.dp),
  ) {
    authorCounts.forEach { (author, count) ->
      val isSelected = selectedAuthorTag == author
      Badge(
        modifier = Modifier.padding(horizontal = 2.dp),
        contentColor = if (isSelected) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onSecondary,
        containerColor = if (isSelected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary,
      ) {
        Row(
          modifier = Modifier.padding(
            horizontal = 4.dp,
            vertical = 4.dp,
          ),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          if (isSelected) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Unselect Author",
              tint = MaterialTheme.colorScheme.onTertiary,
              modifier = Modifier
                .size(20.dp)
                .padding(start = 2.dp),
            )
          }
          Text(
            modifier = Modifier
              .clickable {
                onAuthorTagClick(
                  if (selectedAuthorTag == author) {
                    ""
                  } else if (author == "No Author") {
                    "No Author"
                  } else {
                    author
                  },
                )
              }
              .padding(horizontal = 4.dp, vertical = 4.dp),
            text = "$author ($count)",
            fontSize = 14.sp,
          )
        }
      }
    }
  }
}

internal inline fun ScreenListScope.libraryItems(
  libraries: ImmutableList<StableLibrary>,
  showAuthor: Boolean = true,
  showVersion: Boolean = true,
  showLicenseBadges: Boolean = true,
  colors: LibraryColors,
  padding: LibraryPadding,
  itemContentPadding: PaddingValues = LibraryDefaults.ContentPadding,
  crossinline onLibraryClick: ((Library) -> Unit),
) {
  items(libraries) { library ->
    Library(
      library,
      showAuthor,
      showVersion,
      showLicenseBadges,
      colors,
      padding,
      itemContentPadding,
    ) {
      onLibraryClick.invoke(library.library)
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Library(
  library: StableLibrary,
  showAuthor: Boolean = true,
  showVersion: Boolean = true,
  showLicenseBadges: Boolean = true,
  colors: LibraryColors = LibraryDefaults.libraryColors(),
  padding: LibraryPadding = LibraryDefaults.libraryPadding(),
  contentPadding: PaddingValues = LibraryDefaults.ContentPadding,
  typography: Typography = MaterialTheme.typography,
  onClick: () -> Unit,
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .background(colors.backgroundColor)
      .clickable { onClick.invoke() }
      .padding(contentPadding),
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Text(
        text = library.library.name,
        modifier = Modifier
          .padding(padding.namePadding)
          .weight(1f),
        style = typography.bodyLarge,
        color = colors.contentColor,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
      )
      val version = library.library.artifactVersion
      if (version != null && showVersion) {
        Text(
          version,
          modifier = Modifier.padding(padding.versionPadding),
          style = typography.bodyMedium,
          color = colors.contentColor,
          textAlign = TextAlign.Center,
        )
      }
    }
    Row {
      val author = library.library.author
      if (showAuthor && author.isNotBlank()) {
        Text(
          text = author,
          style = typography.bodyMedium,
          color = colors.contentColor,
        )
      }
      library.library.website?.let { websiteUrl ->
        val uriHandler = LocalUriHandler.current
        Icon(
          imageVector = Icons.Default.OpenInBrowser,
          contentDescription = "Open Library Website",
          tint = MaterialTheme.colorScheme.onBackground,
          modifier = Modifier
            .padding(start = 8.dp)
            .size(20.dp)
            .clickable {
              uriHandler.openUri(websiteUrl)
            },
        )
      }
    }
    if (showLicenseBadges && library.library.licenses.isNotEmpty()) {
      Row {
        library.library.licenses.forEach {
          Badge(
            modifier = Modifier.padding(padding.badgePadding),
            contentColor = colors.badgeContentColor,
            containerColor = colors.badgeBackgroundColor,
          ) {
            Text(
              modifier = Modifier
                .padding(padding.badgeContentPadding)
                .padding(horizontal = 2.dp),
              text = it.name,
            )
          }
        }
      }
    }
  }
}

/**
 * Contains the default values used by [Library]
 */
object LibraryDefaults {
  private val LibraryItemPadding = 16.dp
  private val LibraryNamePaddingTop = 4.dp
  private val LibraryVersionPaddingStart = 8.dp
  private val LibraryBadgePaddingTop = 8.dp
  private val LibraryBadgePaddingEnd = 4.dp
  internal val LibraryItemSpacing = 0.dp

  /**
   * The default content padding used by [Library]
   */
  val ContentPadding = PaddingValues(LibraryItemPadding)

  /**
   * Creates a [LibraryColors] that represents the default colors used in
   * a [Library].
   *
   * @param backgroundColor the background color of this [Library]
   * @param contentColor the content color of this [Library]
   * @param badgeBackgroundColor the badge background color of this [Library]
   * @param badgeContentColor the badge content color of this [Library]
   */
  @Composable
  fun libraryColors(
    backgroundColor: Color = Color.Unspecified,
    contentColor: Color = contentColorFor(backgroundColor),
    badgeBackgroundColor: Color = MaterialTheme.colorScheme.primary,
    badgeContentColor: Color = contentColorFor(badgeBackgroundColor),
  ): LibraryColors = DefaultLibraryColors(
    backgroundColor = backgroundColor,
    contentColor = contentColor,
    badgeBackgroundColor = badgeBackgroundColor,
    badgeContentColor = badgeContentColor,
  )

  /**
   * Creates a [LibraryPadding] that represents the default paddings used in a [Library]
   *
   * @param namePadding the padding around the name shown as part of a [Library]
   * @param versionPadding the padding around the version shown as part of a [Library]
   * @param badgePadding the padding around a badge element shown as part of a [Library]
   * @param badgeContentPadding the padding around the content of a badge element shown as part of a [Library]
   */
  @Composable
  fun libraryPadding(
    namePadding: PaddingValues = PaddingValues(top = LibraryNamePaddingTop),
    versionPadding: PaddingValues = PaddingValues(start = LibraryVersionPaddingStart),
    badgePadding: PaddingValues = PaddingValues(
      top = LibraryBadgePaddingTop,
      end = LibraryBadgePaddingEnd,
    ),
    badgeContentPadding: PaddingValues = PaddingValues(0.dp),
  ): LibraryPadding = DefaultLibraryPadding(
    namePadding = namePadding,
    versionPadding = versionPadding,
    badgePadding = badgePadding,
    badgeContentPadding = badgeContentPadding,
  )
}

/**
 * Represents the background and content colors used in a library.
 */
@Stable
interface LibraryColors {
  /** Represents the background color for this library item. */
  val backgroundColor: Color

  /** Represents the content color for this library item. */
  val contentColor: Color

  /** Represents the badge background color for this library item. */
  val badgeBackgroundColor: Color

  /** Represents the badge content color for this library item. */
  val badgeContentColor: Color
}

/**
 * Default [LibraryColors].
 */
@Immutable
private class DefaultLibraryColors(
  override val backgroundColor: Color,
  override val contentColor: Color,
  override val badgeBackgroundColor: Color,
  override val badgeContentColor: Color,
) : LibraryColors

/**
 * Represents the padding values used in a library.
 */
@Stable
interface LibraryPadding {
  /** Represents the padding around the name shown as part of a [Library] */
  val namePadding: PaddingValues

  /** Represents the padding around the version shown as part of a [Library] */
  val versionPadding: PaddingValues

  /** Represents the padding around a badge element shown as part of a [Library] */
  val badgePadding: PaddingValues

  /** Represents the padding around the content of a badge element shown as part of a [Library] */
  val badgeContentPadding: PaddingValues
}

/**
 * Default [LibraryPadding].
 */
@Immutable
private class DefaultLibraryPadding(
  override val namePadding: PaddingValues,
  override val versionPadding: PaddingValues,
  override val badgePadding: PaddingValues,
  override val badgeContentPadding: PaddingValues,
) : LibraryPadding
