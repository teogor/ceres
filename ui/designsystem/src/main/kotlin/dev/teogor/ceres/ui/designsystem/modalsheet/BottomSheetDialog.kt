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

package dev.teogor.ceres.ui.designsystem.modalsheet

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.ViewRootForInspector
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMaxBy
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*

@Immutable
class BottomSheetDialogProperties(
  val dismissOnBackPress: Boolean = true,
  val dismissOnClickOutside: Boolean = true,
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is BottomSheetDialogProperties) return false

    if (dismissOnBackPress != other.dismissOnBackPress) return false
    if (dismissOnClickOutside != other.dismissOnClickOutside) return false

    return true
  }

  override fun hashCode(): Int {
    var result = dismissOnBackPress.hashCode()
    result = 31 * result + dismissOnClickOutside.hashCode()
    return result
  }
}

@Composable
fun BottomSheetDialog(
  onDismissRequest: () -> Unit,
  properties: BottomSheetDialogProperties = BottomSheetDialogProperties(),
  content: @Composable () -> Unit,
) {
  val view = LocalView.current
  val layoutDirection = LocalLayoutDirection.current

  val bottomSheetDialog = remember {
    BottomSheetDialogWrapper(
      onDismissRequest = onDismissRequest,
      properties = properties,
      composeView = view,
      layoutDirection = layoutDirection,
    ).apply {
      setContent(content)
    }
  }

  DisposableEffect(bottomSheetDialog) {
    bottomSheetDialog.show()

    onDispose {
      bottomSheetDialog.dismiss()
      bottomSheetDialog.disposeComposition()
    }
  }

  SideEffect {
    bottomSheetDialog.updateParameters(
      onDismissRequest = onDismissRequest,
      properties = properties,
      layoutDirection = layoutDirection,
    )
  }
}

private class BottomSheetDialogWrapper(
  private var onDismissRequest: () -> Unit,
  private var properties: BottomSheetDialogProperties,
  composeView: View,
  layoutDirection: LayoutDirection,
) : BottomSheetDialog(composeView.context), ViewRootForInspector {

  private val bottomSheetDialogLayout: BottomSheetDialogLayout

  init {
    val window = window ?: error("Dialog has no window")
    window.requestFeature(Window.FEATURE_NO_TITLE)
    window.setBackgroundDrawableResource(android.R.color.transparent)
    bottomSheetDialogLayout = BottomSheetDialogLayout(context).apply {
      tag = "BottomSheetDialog:${UUID.randomUUID()}"
      clipChildren = false
    }

    fun ViewGroup.disableClipping() {
      clipChildren = false
      if (this is BottomSheetDialogLayout) return
      for (i in 0 until childCount) {
        (getChildAt(i) as? ViewGroup)?.disableClipping()
      }
    }

    (window.decorView as? ViewGroup)?.disableClipping()
    setContentView(bottomSheetDialogLayout)

    setOnDismissListener {
      onDismissRequest()
    }

    setCanceledOnTouchOutside(properties.dismissOnClickOutside)

    updateParameters(onDismissRequest, properties, layoutDirection)
  }

  fun setContent(content: @Composable () -> Unit) {
    bottomSheetDialogLayout.setContent(content)
  }

  private fun setLayoutDirection(layoutDirection: LayoutDirection) {
    bottomSheetDialogLayout.layoutDirection = when (layoutDirection) {
      LayoutDirection.Ltr -> android.util.LayoutDirection.LTR
      LayoutDirection.Rtl -> android.util.LayoutDirection.RTL
    }
  }

  fun updateParameters(
    onDismissRequest: () -> Unit,
    properties: BottomSheetDialogProperties,
    layoutDirection: LayoutDirection,
  ) {
    this.onDismissRequest = onDismissRequest
    this.properties = properties
    setLayoutDirection(layoutDirection)
  }

  fun disposeComposition() {
    bottomSheetDialogLayout.disposeComposition()
  }

  override fun onBackPressed() {
    super.onBackPressed()
    if (properties.dismissOnBackPress) {
      onDismissRequest()
    }
  }
}

@SuppressLint("ViewConstructor")
private class BottomSheetDialogLayout(
  context: Context,
) : AbstractComposeView(context) {

  private var _content: @Composable () -> Unit by mutableStateOf({})

  fun setContent(content: @Composable () -> Unit) {
    this._content = content
    createComposition()
  }

  @Composable
  override fun Content() {
    _content()
  }
}

@Composable
fun BottomSheetDialogLayout(
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit,
) {
  Layout(
    content = content,
    modifier = modifier,
  ) { measurables, constraints ->
    val placeables = measurables.fastMap { it.measure(constraints) }
    val width = placeables.fastMaxBy { it.width }?.width ?: constraints.minWidth
    val height = placeables.fastMaxBy { it.height }?.height ?: constraints.minHeight
    layout(width, height) {
      placeables.fastForEach { it.placeRelative(0, 0) }
    }
  }
}
