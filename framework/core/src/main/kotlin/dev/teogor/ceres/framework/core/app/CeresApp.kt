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

package dev.teogor.ceres.framework.core.app

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavOptions
import dev.teogor.ceres.core.foundation.FoundationGlobal
import dev.teogor.ceres.core.foundation.NetworkMonitor
import dev.teogor.ceres.core.foundation.NetworkMonitorUtility
import dev.teogor.ceres.core.foundation.compositions.LocalNetworkMonitor
import dev.teogor.ceres.data.datastore.defaults.ceresPreferences
import dev.teogor.ceres.navigation.core.LocalNavigationParameters
import dev.teogor.ceres.navigation.core.lib.common.BottomSheetState
import dev.teogor.ceres.navigation.core.lib.common.LocalBottomSheetVM
import dev.teogor.ceres.navigation.core.lib.common.rememberNavigationModules
import dev.teogor.ceres.navigation.core.models.NavigationItem
import dev.teogor.ceres.ui.compose.LocalToolbarState
import dev.teogor.ceres.ui.compose.ToolbarState
import dev.teogor.ceres.ui.designsystem.CeresBackground
import dev.teogor.ceres.ui.designsystem.CeresNavigationBar
import dev.teogor.ceres.ui.designsystem.CeresNavigationBarItem
import dev.teogor.ceres.ui.designsystem.CeresNavigationDefaults
import dev.teogor.ceres.ui.designsystem.CeresNavigationRail
import dev.teogor.ceres.ui.designsystem.CeresNavigationRailItem
import dev.teogor.ceres.ui.designsystem.SystemBarsBox
import dev.teogor.ceres.ui.designsystem.Toolbar
import dev.teogor.ceres.ui.designsystem.modalsheet.ModalBottomSheetLayout
import dev.teogor.ceres.ui.designsystem.modalsheet.ModalBottomSheetValue.Hidden
import dev.teogor.ceres.ui.designsystem.modalsheet.ModalMenuSheetLayout
import dev.teogor.ceres.ui.designsystem.modalsheet.rememberModalBottomSheetState
import dev.teogor.ceres.ui.designsystem.modalsheet.setVisibility
import dev.teogor.ceres.ui.designsystem.modalsheet.toggleVisibility
import dev.teogor.ceres.ui.designsystem.surface
import dev.teogor.ceres.ui.designsystem.surfaceColorAtElevation
import dev.teogor.ceres.ui.foundation.graphics.Icon
import dev.teogor.ceres.ui.foundation.lib.common.emptyComposable
import dev.teogor.ceres.ui.foundation.withTouchFeedback
import dev.teogor.ceres.ui.theme.MaterialTheme
import dev.teogor.ceres.ui.theme.toColor
import dev.teogor.ceres.ui.theme.tokens.ColorSchemeKeyTokens
import dev.teogor.ceres.ui.theme.tokens.ElevationTokens
import kotlinx.coroutines.launch

@SuppressLint("RestrictedApi")
@OptIn(
  ExperimentalComposeUiApi::class,
  ExperimentalMaterialApi::class,
)
@Composable
fun CeresApp(
  windowSizeClass: WindowSizeClass,
  networkMonitor: NetworkMonitorUtility,
  navigationItems: List<NavigationItem> = listOf(),
  appState: CeresAppState = rememberCeresAppState(
    networkMonitor = networkMonitor,
    windowSizeClass = windowSizeClass,
    navigationItems = navigationItems,
  ),
  baseActions: BaseActions = remember {
    BaseActions(appState)
  },
  menuSheetContent: LazyListScope.() -> Unit = {},
  headerContent: @Composable BoxScope.() -> Unit = {},
  content: @Composable (WindowSizeClass, CeresAppState, BaseActions, PaddingValues) -> Unit,
) {
  val ceresPreferences = remember {
    ceresPreferences()
  }

  val navigationModules = rememberNavigationModules(
    content = "",
    bottomNavSheetState = rememberModalBottomSheetState(Hidden),
  )

  // todo VMs
  val bottomSheetVM: BottomSheetState = viewModel()
  val toolbarState: ToolbarState = viewModel()
  val networkMonitor = NetworkMonitor()

  CompositionLocalProvider(
    LocalBottomSheetVM provides bottomSheetVM,
    LocalToolbarState provides toolbarState,
    LocalNetworkMonitor provides networkMonitor,
  ) {
    CeresBackground(
      modifier = Modifier.fillMaxSize(),
    ) {
      val isOffline by appState.isOffline.collectAsStateWithLifecycle()

      LaunchedEffect(isOffline) {
        FoundationGlobal.networkMonitor.isOffline = isOffline
        networkMonitor.isOffline = isOffline
      }

      val menuSheetState = rememberModalBottomSheetState(Hidden)
      val bottomSheetOffset = remember { mutableFloatStateOf(0f) }
      val scope = rememberCoroutineScope()
      // todo not the best way to handle this
      val appStateLocal = LocalNavigationParameters.current
      LaunchedEffect(appStateLocal.isMenuVisible) {
        if (appStateLocal.isMenuVisible != null) {
          menuSheetState.setVisibility(appStateLocal.isMenuVisible!!, scope)
        }
      }
      LaunchedEffect(menuSheetState.isVisible) {
        appStateLocal.isMenuVisible = menuSheetState.isVisible
      }

      val bottomSheetVisible = remember { mutableStateOf(false) }
      SideEffect {
        bottomSheetVisible.value = bottomSheetVM.content.value != emptyComposable
      }
      LaunchedEffect(bottomSheetVM.content.value) {
        val isDefault = bottomSheetVM.content.value == emptyComposable
        if (!isDefault) {
          navigationModules.bottomNav.sheetState.setVisibility(true, scope)
        } else {
          navigationModules.bottomNav.sheetState.setVisibility(false, scope)
        }
      }

      Box(
        modifier = Modifier
          .fillMaxSize(),
      ) {
        val toolbarAlpha by rememberUpdatedState(toolbarState.toolbarAlpha.value)

        // todo snackbar rememberScaffoldState()
        //  look into SnackbarHost
        ModalMenuSheetLayout(
          modifier = Modifier
            .fillMaxWidth(),
          sheetState = menuSheetState,
          sheetContent = menuSheetContent,
          headerContent = headerContent,
        ) {
          Scaffold(
            modifier = Modifier.semantics {
              testTagsAsResourceId = true
            },
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            topBar = {
              val topBarVisible = rememberSaveable(appState.toolbarTokens.visible) {
                appState.toolbarTokens.visible
              }
              if (topBarVisible) {
                val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current
                Toolbar(
                  title = appState.toolbarTokens.title,
                  showBackButton = appState.toolbarTokens.showBackButton,
                  showSettingsButton = appState.toolbarTokens.showSettingsButton,
                  showEditButton = appState.toolbarTokens.showEditButton,
                  showDropDownMenu = appState.toolbarTokens.showDropDownMenu,
                  dropDownItems = appState.toolbarTokens.dropDownItems,
                  buttons = appState.toolbarTokens.buttons,
                  endDigits = ceresPreferences.name,
                  onBackClicked = {
                    onBackPressedDispatcher?.onBackPressedDispatcher?.onBackPressed()
                  },
                  // todo rename[param]: triggerAlpha
                  // triggerAlpha = 1f - toolbarAlphaValue,
                  onOpenMenu = {
                    scope.launch {
                      menuSheetState.show()
                    }
                  },
                  handleElevation = {
                    val elevation = if (navigationModules.bottomNav.isVisible()) {
                      val elevation by animateDpAsState(
                        targetValue = if (bottomSheetOffset.floatValue < 0.01f) {
                          CeresNavigationDefaults.Elevation
                        } else {
                          ElevationTokens.Level1
                        },
                        label = "ToolbarElevationAnimation",
                      )
                      elevation
                    } else {
                      val elevation = if (toolbarAlpha == 0f) {
                        ElevationTokens.Level1
                      } else {
                        val elevationRange =
                          CeresNavigationDefaults.Elevation - ElevationTokens.Level1
                        val elevationStep = elevationRange / 5

                        val elevationLevel = (toolbarAlpha * 5).toInt()
                        ElevationTokens.Level1 + (elevationStep * elevationLevel)
                      }
                      elevation
                    }

                    toolbarState.updateElevation(elevation)
                    toolbarState.updateToolbarColor(
                      surfaceColorAtElevation(
                        elevation = elevation,
                        color = MaterialTheme.colorScheme.surface,
                      ),
                    )

                    elevation
                  },
                )
              }
            },
            bottomBar = {
              if (navigationItems.isNotEmpty()) {
                val bottomBarVisible = rememberSaveable(
                  bottomSheetVisible.value,
                  appState.shouldShowBottomBar,
                  appState.shouldShowNavBar,
                ) {
                  !bottomSheetVisible.value && appState.shouldShowBottomBar && appState.shouldShowNavBar
                }
                if (bottomBarVisible) {
                  CeresBottomBar(
                    destinations = appState.navigationItems,
                    onNavigateToDestination = appState::navigateToNavigationItem,
                    currentDestination = appState.currentDestination,
                    modifier = Modifier.testTag("CeresBottomBar"),
                  )
                }
              }
            },
            snackbarHost = {
              val snackbarVisible = rememberSaveable(
                bottomSheetVisible.value,
                appState.snackbarView.isVisible,
              ) {
                !bottomSheetVisible.value && appState.snackbarView.isVisible
              }
              val snackbarContent = rememberSaveable(
                appState.snackbarView.content,
              ) {
                appState.snackbarView.content
              }
              val snackbarContentChanged = remember(snackbarVisible, snackbarContent) {
                snackbarVisible && snackbarContent.isNotEmpty()
              }
              if (snackbarVisible) {
                Box(
                  modifier = Modifier
                    .padding(bottom = 10.dp)
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth()
                    .surface(
                      shape = RoundedCornerShape(16.dp),
                      backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                      border = null, // BorderStroke
                      shadowElevation = 10.dp,
                    )
                    .heightIn(min = 60.dp, max = 100.dp)
                    .padding(vertical = 10.dp, horizontal = 20.dp),
                  contentAlignment = Alignment.Center,
                ) {
                  Text(
                    text = snackbarContent,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    fontSize = 14.sp,
                  )
                }
              }
            },
            floatingActionButton = {
              val fabVisible = rememberSaveable(
                bottomSheetVisible.value,
                appState.floatingButtonView.isVisible,
              ) {
                !bottomSheetVisible.value && appState.floatingButtonView.isVisible
              }
              if (fabVisible) {
                FloatingActionButton(
                  onClick = { appState.fabClicked() }.withTouchFeedback(LocalContext.current),
                  containerColor = ColorSchemeKeyTokens.PrimaryContainer.toColor(),
                  content = {
                    Icon(
                      appState.floatingButtonView.icon,
                      contentDescription = appState.floatingButtonView.contentDescription,
                    )
                  },
                )
              }
            },
          ) { padding ->
            val innerPadding = padding.calculateInnerPadding()
            if (navigationItems.isNotEmpty()) {
              if (!navigationModules.bottomNav.isVisible() && appState.shouldShowNavRail && appState.shouldShowNavBar) {
                CeresNavRail(
                  destinations = appState.navigationItems,
                  onNavigateToDestination = appState::navigateToNavigationItem,
                  currentDestination = appState.currentDestination,
                  modifier = Modifier
                    .testTag("CeresNavRail")
                    .safeDrawingPadding(),
                )
              }
            }

            val paddingTop = innerPadding.calculateTopPadding()
            ModalBottomSheetLayout(
              modifier = Modifier
                .fillMaxWidth(),
              sheetState = navigationModules.bottomNav.sheetState,
              hasTopPadding = !appState.toolbarTokens.visible,
              paddingTop = paddingTop,
              onOffsetChange = {
                bottomSheetOffset.floatValue = it
              },
              onIsHiding = {
                bottomSheetVM.setIsHiding(it)
              },
              sheetContent = {
                bottomSheetVM.content.value.invoke(this)
              },
            ) {
              val isNavigationBarVisible = remember(appState.isNavigationBarVisible) {
                appState.isNavigationBarVisible
              }
              val isStatusBarVisible = remember(
                appState.isStatusBarVisible,
                appState.toolbarTokens.visible,
              ) {
                appState.isStatusBarVisible && !appState.toolbarTokens.visible
              }
              SystemBarsBox(
                isStatusBarVisible = isStatusBarVisible,
                isNavigationBarVisible = isNavigationBarVisible,
              ) {
                var shouldCloseApp by remember { mutableStateOf(false) }

                BackHandler {
                  if (navigationModules.bottomNav.sheetState.isVisible) {
                    navigationModules.bottomNav.sheetState.toggleVisibility(scope)
                  } else if (menuSheetState.isVisible) {
                    menuSheetState.toggleVisibility(scope)
                  } else {
                    val backStack: List<NavBackStackEntry> =
                      appState.navController.currentBackStack
                        .value
                    if (!appState.navController.popBackStack() || backStack.size <= 1) {
                      shouldCloseApp = true
                    }
                  }
                }

                if (shouldCloseApp) {
                  val context = LocalContext.current
                  val currentActivity = context as? Activity
                  currentActivity?.finish()
                }

                val bottomPadding = remember(
                  appState.floatingButtonView.isVisible,
                ) {
                  if (appState.floatingButtonView.isVisible) {
                    80.dp
                  } else {
                    0.dp
                  }
                }
                LaunchedEffect(bottomPadding) {
                  bottomSheetVM.setBottomSpace(bottomPadding)
                }

                content(windowSizeClass, appState, baseActions, innerPadding)
              }
            }
          }
        }
      }
    }
  }
}

private fun CeresAppState.fabClicked(navOptions: NavOptions? = null) {
  this.floatingButtonView.let { fab ->
    fab.screenRoute?.let { this.navController.navigate(it.route, navOptions) }
    fab.action?.invoke()
  }
}

@Composable
fun PaddingValues.calculateInnerPadding(
  layoutDirection: LayoutDirection = LocalLayoutDirection.current,
  extraTop: Dp = 0.dp,
  extraBottom: Dp = 0.dp,
  extraStart: Dp = 0.dp,
  extraEnd: Dp = 0.dp,
): PaddingValues {
  return PaddingValues(
    top = calculateTopPadding() + extraTop,
    bottom = calculateBottomPadding() + extraBottom,
    start = calculateStartPadding(layoutDirection) + extraStart,
    end = calculateEndPadding(layoutDirection) + extraEnd,
  )
}

@Composable
private fun CeresNavRail(
  destinations: List<NavigationItem>,
  onNavigateToDestination: (NavigationItem) -> Unit,
  currentDestination: NavDestination?,
  modifier: Modifier = Modifier,
) {
  CeresNavigationRail(modifier = modifier) {
    destinations.forEach { destination ->
      val selected = currentDestination.isNavigationItemInHierarchy(destination)
      CeresNavigationRailItem(
        selected = selected,
        onClick = { onNavigateToDestination(destination) },
        icon = {
          val icon = if (selected) {
            destination.selectedIcon
          } else {
            destination.unselectedIcon
          }
          when (icon) {
            is Icon.ImageVectorIcon -> Icon(
              imageVector = icon.imageVector,
              contentDescription = null,
            )

            is Icon.DrawableResourceIcon -> Icon(
              painter = painterResource(id = icon.id),
              contentDescription = null,
            )
          }
        },
        label = { Text(destination.titleText) },
      )
    }
  }
}

@Composable
private fun CeresBottomBar(
  destinations: List<NavigationItem>,
  onNavigateToDestination: (NavigationItem) -> Unit,
  currentDestination: NavDestination?,
  modifier: Modifier = Modifier,
) {
  CeresNavigationBar(
    modifier = modifier,
  ) {
    destinations.forEach { destination ->
      val selected = currentDestination.isNavigationItemInHierarchy(destination)
      CeresNavigationBarItem(
        selected = selected,
        onClick = { onNavigateToDestination(destination) }.withTouchFeedback(LocalContext.current),
        icon = {
          val icon = if (selected) {
            destination.selectedIcon
          } else {
            destination.unselectedIcon
          }
          when (icon) {
            is Icon.ImageVectorIcon -> Icon(
              imageVector = icon.imageVector,
              contentDescription = null,
            )

            is Icon.DrawableResourceIcon -> Icon(
              painter = painterResource(id = icon.id),
              contentDescription = null,
            )
          }
        },
        label = destination.iconText,
        // todo alwaysShowLabel = false,
      )
    }
  }
}

private fun NavDestination?.isNavigationItemInHierarchy(destination: NavigationItem) =
  this?.hierarchy?.any {
    destination.screenRoute.let { it1 -> it.route?.contains(it1.route, true) } ?: false
  } ?: false
