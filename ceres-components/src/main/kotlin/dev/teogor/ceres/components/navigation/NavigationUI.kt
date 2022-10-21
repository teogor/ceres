/*
 * Copyright 2022 teogor (Teodor Grigor) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.teogor.ceres.components.navigation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.RestrictTo
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.forEach
import androidx.customview.widget.Openable
import androidx.fragment.app.FragmentActivity
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigationrail.NavigationRailView
import dev.teogor.ceres.components.app.BaseFragment
import dev.teogor.ceres.core.global.GlobalData
import dev.teogor.ceres.core.logger.Logger
import dev.teogor.ceres.extensions.currentNavigationFragment
import java.lang.ref.WeakReference

object NavigationUI : Logger {
  /**
   * Attempt to navigate to the [NavDestination] associated with the given MenuItem. This
   * MenuItem should have been added via one of the helper methods in this class.
   *
   * Importantly, it assumes the [menu item id][MenuItem.getItemId] matches a valid
   * [action id][NavDestination.getAction] or [destination id][NavDestination.id] to be
   * navigated to.
   *
   * By default, the back stack will be popped back to the navigation graph's start destination.
   * Menu items that have `android:menuCategory="secondary"` will not pop the back
   * stack.
   *
   * @param item The selected MenuItem.
   * @param navController The NavController that hosts the destination.
   * @return True if the [NavController] was able to navigate to the destination
   * associated with the given MenuItem.
   */
  @JvmStatic
  public fun onNavDestinationSelected(item: MenuItem, navController: NavController): Boolean {
    if (item.itemId == navController.currentBackStackEntry!!.destination.id) {
      if (GlobalData.activity is FragmentActivity) {
        val frgActivity = GlobalData.activity as FragmentActivity
        val currentFrg = frgActivity.supportFragmentManager.currentNavigationFragment
        (currentFrg as BaseFragment<*, *>).onRefresh()
      }
      return false
    }
    val builder = NavOptions.Builder()
      .setLaunchSingleTop(true)
      .setRestoreState(true)
    // todo set @integer/config_navAnimTime programmatically
    if (
      navController.currentDestination!!.parent!!.findNode(item.itemId)
      is ActivityNavigator.Destination
    ) {
      builder.setEnterAnim(R.anim.nav_default_enter_anim)
        .setExitAnim(R.anim.nav_default_exit_anim)
        .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
        .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
    } else {
      builder.setEnterAnim(R.animator.nav_default_enter_anim)
        .setExitAnim(R.animator.nav_default_exit_anim)
        .setPopEnterAnim(R.animator.nav_default_pop_enter_anim)
        .setPopExitAnim(R.animator.nav_default_pop_exit_anim)
    }
    if (item.order and Menu.CATEGORY_SECONDARY == 0) {
      // fixme not sure if this works in nested bottom_bar_behaviors
      //  check this - https://www.valueof.io/blog/bottom-nav-with-nav-graphs-jetpack-compose
      builder.setPopUpTo(
        navController.graph.id,
        inclusive = false,
        saveState = true
      )
    }
    val options = builder.build()
    return try {
      // TODO provide proper API instead of using Exceptions as Control-Flow.
      navController.navigate(item.itemId, null, options)
      // Return true only if the destination we've navigated to matches the MenuItem
      navController.currentDestination?.matchDestination(item.itemId) == true
    } catch (e: IllegalArgumentException) {
      false
    }
  }

  /**
   * Attempt to navigate to the [NavDestination] associated with the given MenuItem. This
   * MenuItem should have been added via one of the helper methods in this class.
   *
   * Importantly, it assumes the [menu item id][MenuItem.getItemId] matches a valid
   * [action id][NavDestination.getAction] or [destination id][NavDestination.id] to be
   * navigated to.
   *
   * By default, the back stack will be popped back to the navigation graph's start destination.
   * Menu items that have `android:menuCategory="secondary"` will not pop the back
   * stack.
   *
   * @param item The selected MenuItem.
   * @param navController The NavController that hosts the destination.
   * @param saveState Whether the NavController should save the back stack state. This must
   * always be `false`: leave this parameter off entirely to use the non-experimental version
   * of this API, which saves the state by default.
   *
   * @return True if the [NavController] was able to navigate to the destination
   * associated with the given MenuItem.
   */
  @NavigationUiSaveStateControl
  @JvmStatic
  public fun onNavDestinationSelected(
    item: MenuItem,
    navController: NavController,
    saveState: Boolean
  ): Boolean {
    check(!saveState) {
      "Leave the saveState parameter out entirely to use the non-experimental version of " +
        "this API, which saves the state by default"
    }
    val builder = NavOptions.Builder().setLaunchSingleTop(true)
    if (
      navController.currentDestination!!.parent!!.findNode(item.itemId)
      is ActivityNavigator.Destination
    ) {
      builder.setEnterAnim(R.anim.nav_default_enter_anim)
        .setExitAnim(R.anim.nav_default_exit_anim)
        .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
        .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
    } else {
      builder.setEnterAnim(R.animator.nav_default_enter_anim)
        .setExitAnim(R.animator.nav_default_exit_anim)
        .setPopEnterAnim(R.animator.nav_default_pop_enter_anim)
        .setPopExitAnim(R.animator.nav_default_pop_exit_anim)
    }
    if (item.order and Menu.CATEGORY_SECONDARY == 0) {
      builder.setPopUpTo(
        navController.graph.findStartDestination().id,
        inclusive = false
      )
    }
    val options = builder.build()
    return try {
      // TODO provide proper API instead of using Exceptions as Control-Flow.
      navController.navigate(item.itemId, null, options)
      // Return true only if the destination we've navigated to matches the MenuItem
      navController.currentDestination?.matchDestination(item.itemId) == true
    } catch (e: IllegalArgumentException) {
      false
    }
  }

  /**
   * Handles the Up button by delegating its behavior to the given NavController. This should
   * generally be called from [AppCompatActivity.onSupportNavigateUp].
   *
   * If you do not have a [Openable] layout, you should call
   * [NavController.navigateUp] directly.
   *
   * @param navController The NavController that hosts your content.
   * @param openableLayout The Openable layout that should be opened if you are on the topmost
   * level of the app.
   * @return True if the [NavController] was able to navigate up.
   */
  @JvmStatic
  public fun navigateUp(navController: NavController, openableLayout: Openable?): Boolean =
    navigateUp(
      navController,
      AppBarConfiguration.Builder(navController.graph)
        .setOpenableLayout(openableLayout)
        .build()
    )

  /**
   * Handles the Up button by delegating its behavior to the given NavController. This is
   * an alternative to using [NavController.navigateUp] directly when the given
   * [AppBarConfiguration] needs to be considered when determining what should happen
   * when the Up button is pressed.
   *
   * In cases where no Up action is available, the
   * [AppBarConfiguration.fallbackOnNavigateUpListener] will be called to provide
   * additional control.
   *
   * @param navController The NavController that hosts your content.
   * @param configuration Additional configuration options for determining what should happen
   * when the Up button is pressed.
   * @return True if the [NavController] was able to navigate up.
   */
  @JvmStatic
  public fun navigateUp(
    navController: NavController,
    configuration: AppBarConfiguration
  ): Boolean {
    val openableLayout = configuration.openableLayout
    val currentDestination = navController.currentDestination
    val topLevelDestinations = configuration.topLevelDestinations
    return if (openableLayout != null && currentDestination != null &&
      currentDestination.matchDestinations(topLevelDestinations)
    ) {
      openableLayout.open()
      true
    } else {
      return if (navController.navigateUp()) {
        true
      } else configuration.fallbackOnNavigateUpListener?.onNavigateUp() ?: false
    }
  }

  /**
   * Sets up a [NavigationView] for use with a [NavController]. This will call
   * [onNavDestinationSelected] when a menu item is selected.
   * The selected item in the NavigationView will automatically be updated when the destination
   * changes.
   *
   * If the [NavigationView] is directly contained with an [Openable] layout,
   * it will be closed when a menu item is selected.
   *
   * Similarly, if the [NavigationView] has a [BottomSheetBehavior] associated with
   * it (as is the case when using a [com.google.android.material.bottomsheet.BottomSheetDialog]),
   * the bottom sheet will be hidden when a menu item is selected.
   *
   * @param navigationView The NavigationView that should be kept in sync with changes to the
   * NavController.
   * @param navController The NavController that supplies the primary and secondary menu.
   * Navigation actions on this NavController will be reflected in the
   * selected item in the NavigationView.
   */
  @JvmStatic
  fun setupWithNavController(
    navigationView: NavigationView,
    navController: NavController
  ) {
    navigationView.setNavigationItemSelectedListener { item ->
      val handled = onNavDestinationSelected(item, navController)
      if (handled) {
        val parent = navigationView.parent
        if (parent is Openable) {
          parent.close()
        } else {
          val bottomSheetBehavior = findBottomSheetBehavior(navigationView)
          if (bottomSheetBehavior != null) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
          }
        }
      }
      handled
    }
    val weakReference = WeakReference(navigationView)
    navController.addOnDestinationChangedListener(
      object : NavController.OnDestinationChangedListener {
        override fun onDestinationChanged(
          controller: NavController,
          destination: NavDestination,
          arguments: Bundle?
        ) {
          val view = weakReference.get()
          if (view == null) {
            navController.removeOnDestinationChangedListener(this)
            return
          }
          view.menu.forEach { item ->
            item.isChecked = destination.matchDestination(item.itemId)
          }
        }
      }
    )
  }

  /**
   * Sets up a [NavigationView] for use with a [NavController]. This will call
   * [onNavDestinationSelected] when a menu item is selected.
   * The selected item in the NavigationView will automatically be updated when the destination
   * changes.
   *
   * If the [NavigationView] is directly contained with an [Openable] layout,
   * it will be closed when a menu item is selected.
   *
   * Similarly, if the [NavigationView] has a [BottomSheetBehavior] associated with
   * it (as is the case when using a [com.google.android.material.bottomsheet.BottomSheetDialog]),
   * the bottom sheet will be hidden when a menu item is selected.
   *
   * @param navigationView The NavigationView that should be kept in sync with changes to the
   * NavController.
   * @param navController The NavController that supplies the primary and secondary menu.
   * @param saveState Whether the NavController should save the back stack state. This must
   * always be `false`: leave this parameter off entirely to use the non-experimental version
   * of this API, which saves the state by default.
   *
   * Navigation actions on this NavController will be reflected in the
   * selected item in the NavigationView.
   */
  @NavigationUiSaveStateControl
  @JvmStatic
  public fun setupWithNavController(
    navigationView: NavigationView,
    navController: NavController,
    saveState: Boolean
  ) {
    check(!saveState) {
      "Leave the saveState parameter out entirely to use the non-experimental version of " +
        "this API, which saves the state by default"
    }
    navigationView.setNavigationItemSelectedListener { item ->
      val handled = onNavDestinationSelected(item, navController, saveState)
      if (handled) {
        val parent = navigationView.parent
        if (parent is Openable) {
          parent.close()
        } else {
          val bottomSheetBehavior = findBottomSheetBehavior(navigationView)
          if (bottomSheetBehavior != null) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
          }
        }
      }
      handled
    }
    val weakReference = WeakReference(navigationView)
    navController.addOnDestinationChangedListener(
      object : NavController.OnDestinationChangedListener {
        override fun onDestinationChanged(
          controller: NavController,
          destination: NavDestination,
          arguments: Bundle?
        ) {
          val view = weakReference.get()
          if (view == null) {
            navController.removeOnDestinationChangedListener(this)
            return
          }
          view.menu.forEach { item ->
            item.isChecked = destination.matchDestination(item.itemId)
          }
        }
      }
    )
  }

  /**
   * Walks up the view hierarchy, trying to determine if the given View is contained within
   * a bottom sheet.
   * @suppress
   */
  @JvmStatic
  @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
  public fun findBottomSheetBehavior(view: View): BottomSheetBehavior<*>? {
    val params = view.layoutParams
    if (params !is CoordinatorLayout.LayoutParams) {
      val parent = view.parent
      return if (parent is View) {
        findBottomSheetBehavior(parent as View)
      } else null
    }
    val behavior = params
      .behavior
    return if (behavior !is BottomSheetBehavior<*>) {
      // We hit a CoordinatorLayout, but the View doesn't have the BottomSheetBehavior
      null
    } else behavior
  }

  /**
   * Sets up a [NavigationBarView] for use with a [NavController]. This will call
   * [onNavDestinationSelected] when a menu item is selected. The
   * selected item in the NavigationBarView will automatically be updated when the destination
   * changes.
   *
   * @param navigationBarView The NavigationBarView ([BottomNavigationView] or
   * [NavigationRailView])
   * that should be kept in sync with changes to the NavController.
   * @param navController The NavController that supplies the primary menu.
   * Navigation actions on this NavController will be reflected in the
   * selected item in the NavigationBarView.
   */
  @JvmStatic
  fun setupWithNavController(
    navigationBarView: NavigationBarView,
    navController: NavController
  ) {
    navigationBarView.setOnItemSelectedListener { item ->
      onNavDestinationSelected(
        item,
        navController
      )
    }
    val weakReference = WeakReference(navigationBarView)
    navController.addOnDestinationChangedListener(
      object : NavController.OnDestinationChangedListener {
        override fun onDestinationChanged(
          controller: NavController,
          destination: NavDestination,
          arguments: Bundle?
        ) {
          val view = weakReference.get()
          if (view == null) {
            navController.removeOnDestinationChangedListener(this)
            return
          }
          view.menu.forEach { item ->
            if (destination.matchDestination(item.itemId)) {
              item.isChecked = true
            }
          }
        }
      }
    )
  }

  /**
   * Sets up a [NavigationBarView] for use with a [NavController]. This will call
   * [onNavDestinationSelected] when a menu item is selected. The
   * selected item in the NavigationBarView will automatically be updated when the destination
   * changes.
   *
   * @param navigationBarView The NavigationBarView ([BottomNavigationView] or
   * [NavigationRailView])
   * that should be kept in sync with changes to the NavController.
   * @param navController The NavController that supplies the primary menu.
   * @param saveState Whether the NavController should save the back stack state. This must
   * always be `false`: leave this parameter off entirely to use the non-experimental version
   * of this API, which saves the state by default.
   *
   * Navigation actions on this NavController will be reflected in the
   * selected item in the NavigationBarView.
   */
  @NavigationUiSaveStateControl
  @JvmStatic
  public fun setupWithNavController(
    navigationBarView: NavigationBarView,
    navController: NavController,
    saveState: Boolean
  ) {
    check(!saveState) {
      "Leave the saveState parameter out entirely to use the non-experimental version of " +
        "this API, which saves the state by default"
    }
    navigationBarView.setOnItemSelectedListener { item ->
      onNavDestinationSelected(item, navController, saveState)
    }
    val weakReference = WeakReference(navigationBarView)
    navController.addOnDestinationChangedListener(
      object : NavController.OnDestinationChangedListener {
        override fun onDestinationChanged(
          controller: NavController,
          destination: NavDestination,
          arguments: Bundle?
        ) {
          val view = weakReference.get()
          if (view == null) {
            navController.removeOnDestinationChangedListener(this)
            return
          }
          view.menu.forEach { item ->
            if (destination.matchDestination(item.itemId)) {
              item.isChecked = true
            }
          }
        }
      }
    )
  }

  /**
   * Determines whether the given `destId` matches the NavDestination. This handles
   * both the default case (the destination's id matches the given id) and the nested case where
   * the given id is a parent/grandparent/etc of the destination.
   */
  @JvmStatic
  internal fun NavDestination.matchDestination(@IdRes destId: Int): Boolean =
    hierarchy.any { it.id == destId }

  /**
   * Determines whether the given `destinationIds` match the NavDestination. This
   * handles both the default case (the destination's id is in the given ids) and the nested
   * case where the given ids is a parent/grandparent/etc of the destination.
   */
  @JvmStatic
  internal fun NavDestination.matchDestinations(destinationIds: Set<Int?>): Boolean =
    hierarchy.any { destinationIds.contains(it.id) }
}
