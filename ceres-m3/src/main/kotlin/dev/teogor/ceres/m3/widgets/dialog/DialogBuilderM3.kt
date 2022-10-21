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

package dev.teogor.ceres.m3.widgets.dialog

import android.content.Context
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.database.Cursor
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.widget.AdapterView
import android.widget.ListAdapter
import androidx.annotation.ArrayRes
import androidx.annotation.AttrRes
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.view.ViewCompat
import com.google.android.material.resources.MaterialAttributes
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.theme.overlay.MaterialThemeOverlay
import dev.teogor.ceres.m3.elevation.SurfaceLevel
import dev.teogor.ceres.m3.theme.ElevationOverlay
import dev.teogor.ceres.m3.theme.ThemeM3.currentColorScheme
import dev.teogor.ceres.m3.widgets.dialog.MaterialDialogs.getDialogBackgroundInsets
import dev.teogor.ceres.m3.widgets.dialog.MaterialDialogs.insetDrawable

/**
 * An extension of [AlertDialog.Builder] for use with a Material theme (e.g.,
 * Theme.MaterialComponents).
 *
 *
 * This Builder must be used in order for AlertDialog objects to respond to color and shape
 * theming provided by Material themes.
 *
 *
 * The type of dialog returned is still an [AlertDialog]; there is no specific Material
 * implementation of [AlertDialog].
 */
open class DialogBuilderM3(
  var wrapContext: Context,
  overrideThemeResId: Int = 0
) : AlertDialog.Builder(
  createMaterialAlertDialogThemedContext(wrapContext),
  getOverridingThemeResId(wrapContext, overrideThemeResId)
) {
  var background: Drawable?
    private set

  private val backgroundInsets: Rect

  init {
    // Ensure we are using the correctly themed context rather than the context that was passed in.
    wrapContext = context
    val theme = wrapContext.theme
    backgroundInsets = getDialogBackgroundInsets(wrapContext, DEF_STYLE_ATTR, DEF_STYLE_RES)
    val surfaceColor = ElevationOverlay(wrapContext, currentColorScheme()).compositeOverlay(
      SurfaceLevel.Lvl3
    )
    val materialShapeDrawable = MaterialShapeDrawable(
      wrapContext,
      null,
      DEF_STYLE_ATTR,
      DEF_STYLE_RES
    )
    materialShapeDrawable.initializeElevationOverlay(wrapContext)
    materialShapeDrawable.fillColor = ColorStateList.valueOf(surfaceColor)

    // dialogCornerRadius first appeared in Android Pie
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      val dialogCornerRadiusValue = TypedValue()
      theme.resolveAttribute(android.R.attr.dialogCornerRadius, dialogCornerRadiusValue, true)
      val dialogCornerRadius =
        dialogCornerRadiusValue.getDimension(context.resources.displayMetrics)
      if (dialogCornerRadiusValue.type == TypedValue.TYPE_DIMENSION && dialogCornerRadius >= 0) {
        materialShapeDrawable.setCornerSize(dialogCornerRadius)
      }
    }
    background = materialShapeDrawable
  }

  override fun create(): AlertDialog {
    val alertDialog = super.create()
    val window = alertDialog.window
    /* {@link Window#getDecorView()} should be called before any changes are made to the Window
     * as it locks in attributes and affects layout. */
    val decorView = window!!.decorView
    if (background is MaterialShapeDrawable) {
      (background as MaterialShapeDrawable).elevation = ViewCompat.getElevation(decorView)
    }
    val insetDrawable: Drawable = insetDrawable(background, backgroundInsets)
    window.setBackgroundDrawable(insetDrawable)
    decorView.setOnTouchListener(InsetDialogOnTouchListener(alertDialog, backgroundInsets))
    return alertDialog
  }

  fun setBackground(background: Drawable?): DialogBuilderM3 {
    this.background = background
    return this
  }

  fun setBackgroundInsetStart(@Px backgroundInsetStart: Int): DialogBuilderM3 {
    if (context.resources.configuration.layoutDirection == ViewCompat.LAYOUT_DIRECTION_RTL) {
      backgroundInsets.right = backgroundInsetStart
    } else {
      backgroundInsets.left = backgroundInsetStart
    }
    return this
  }

  fun setBackgroundInsetTop(@Px backgroundInsetTop: Int): DialogBuilderM3 {
    backgroundInsets.top = backgroundInsetTop
    return this
  }

  fun setBackgroundInsetEnd(@Px backgroundInsetEnd: Int): DialogBuilderM3 {
    if (context.resources.configuration.layoutDirection == ViewCompat.LAYOUT_DIRECTION_RTL) {
      backgroundInsets.left = backgroundInsetEnd
    } else {
      backgroundInsets.right = backgroundInsetEnd
    }
    return this
  }

  fun setBackgroundInsetBottom(@Px backgroundInsetBottom: Int): DialogBuilderM3 {
    backgroundInsets.bottom = backgroundInsetBottom
    return this
  }

  // The following methods are all pass-through methods used to specify the return type for the
  // builder chain.
  override fun setTitle(@StringRes titleId: Int): DialogBuilderM3 {
    return super.setTitle(titleId) as DialogBuilderM3
  }

  override fun setTitle(title: CharSequence?): DialogBuilderM3 {
    return super.setTitle(title) as DialogBuilderM3
  }

  override fun setCustomTitle(customTitleView: View?): DialogBuilderM3 {
    return super.setCustomTitle(customTitleView) as DialogBuilderM3
  }

  override fun setMessage(@StringRes messageId: Int): DialogBuilderM3 {
    return super.setMessage(messageId) as DialogBuilderM3
  }

  override fun setMessage(message: CharSequence?): DialogBuilderM3 {
    return super.setMessage(message) as DialogBuilderM3
  }

  override fun setIcon(@DrawableRes iconId: Int): DialogBuilderM3 {
    return super.setIcon(iconId) as DialogBuilderM3
  }

  override fun setIcon(icon: Drawable?): DialogBuilderM3 {
    return super.setIcon(icon) as DialogBuilderM3
  }

  override fun setIconAttribute(@AttrRes attrId: Int): DialogBuilderM3 {
    return super.setIconAttribute(attrId) as DialogBuilderM3
  }

  override fun setPositiveButton(
    @StringRes textId: Int,
    listener: DialogInterface.OnClickListener?
  ): DialogBuilderM3 {
    return super.setPositiveButton(textId, listener) as DialogBuilderM3
  }

  override fun setPositiveButton(
    text: CharSequence?,
    listener: DialogInterface.OnClickListener?
  ): DialogBuilderM3 {
    return super.setPositiveButton(text, listener) as DialogBuilderM3
  }

  override fun setPositiveButtonIcon(icon: Drawable?): DialogBuilderM3 {
    return super.setPositiveButtonIcon(icon) as DialogBuilderM3
  }

  override fun setNegativeButton(
    @StringRes textId: Int,
    listener: DialogInterface.OnClickListener?
  ): DialogBuilderM3 {
    return super.setNegativeButton(textId, listener) as DialogBuilderM3
  }

  override fun setNegativeButton(
    text: CharSequence?,
    listener: DialogInterface.OnClickListener?
  ): DialogBuilderM3 {
    return super.setNegativeButton(text, listener) as DialogBuilderM3
  }

  override fun setNegativeButtonIcon(icon: Drawable?): DialogBuilderM3 {
    return super.setNegativeButtonIcon(icon) as DialogBuilderM3
  }

  override fun setNeutralButton(
    @StringRes textId: Int,
    listener: DialogInterface.OnClickListener?
  ): DialogBuilderM3 {
    return super.setNeutralButton(textId, listener) as DialogBuilderM3
  }

  override fun setNeutralButton(
    text: CharSequence?,
    listener: DialogInterface.OnClickListener?
  ): DialogBuilderM3 {
    return super.setNeutralButton(text, listener) as DialogBuilderM3
  }

  override fun setNeutralButtonIcon(icon: Drawable?): DialogBuilderM3 {
    return super.setNeutralButtonIcon(icon) as DialogBuilderM3
  }

  override fun setCancelable(cancelable: Boolean): DialogBuilderM3 {
    return super.setCancelable(cancelable) as DialogBuilderM3
  }

  override fun setOnCancelListener(
    onCancelListener: DialogInterface.OnCancelListener?
  ): DialogBuilderM3 {
    return super.setOnCancelListener(onCancelListener) as DialogBuilderM3
  }

  override fun setOnDismissListener(
    onDismissListener: DialogInterface.OnDismissListener?
  ): DialogBuilderM3 {
    return super.setOnDismissListener(onDismissListener) as DialogBuilderM3
  }

  override fun setOnKeyListener(onKeyListener: DialogInterface.OnKeyListener?): DialogBuilderM3 {
    return super.setOnKeyListener(onKeyListener) as DialogBuilderM3
  }

  override fun setItems(
    @ArrayRes itemsId: Int,
    listener: DialogInterface.OnClickListener?
  ): DialogBuilderM3 {
    return super.setItems(itemsId, listener) as DialogBuilderM3
  }

  override fun setItems(
    items: Array<CharSequence>?,
    listener: DialogInterface.OnClickListener?
  ): DialogBuilderM3 {
    return super.setItems(items, listener) as DialogBuilderM3
  }

  override fun setAdapter(
    adapter: ListAdapter?,
    listener: DialogInterface.OnClickListener?
  ): DialogBuilderM3 {
    return super.setAdapter(adapter, listener) as DialogBuilderM3
  }

  override fun setCursor(
    cursor: Cursor?,
    listener: DialogInterface.OnClickListener?,
    labelColumn: String
  ): DialogBuilderM3 {
    return super.setCursor(cursor, listener, labelColumn) as DialogBuilderM3
  }

  override fun setMultiChoiceItems(
    @ArrayRes itemsId: Int,
    checkedItems: BooleanArray?,
    listener: DialogInterface.OnMultiChoiceClickListener?
  ): DialogBuilderM3 {
    return super.setMultiChoiceItems(itemsId, checkedItems, listener) as DialogBuilderM3
  }

  override fun setMultiChoiceItems(
    items: Array<CharSequence>?,
    checkedItems: BooleanArray?,
    listener: DialogInterface.OnMultiChoiceClickListener?
  ): DialogBuilderM3 {
    return super.setMultiChoiceItems(items, checkedItems, listener) as DialogBuilderM3
  }

  override fun setMultiChoiceItems(
    cursor: Cursor?,
    isCheckedColumn: String,
    labelColumn: String,
    listener: DialogInterface.OnMultiChoiceClickListener?
  ): DialogBuilderM3 {
    return super.setMultiChoiceItems(
      cursor,
      isCheckedColumn,
      labelColumn,
      listener
    ) as DialogBuilderM3
  }

  override fun setSingleChoiceItems(
    @ArrayRes itemsId: Int,
    checkedItem: Int,
    listener: DialogInterface.OnClickListener?
  ): DialogBuilderM3 {
    return super.setSingleChoiceItems(itemsId, checkedItem, listener) as DialogBuilderM3
  }

  override fun setSingleChoiceItems(
    cursor: Cursor?,
    checkedItem: Int,
    labelColumn: String,
    listener: DialogInterface.OnClickListener?
  ): DialogBuilderM3 {
    return super.setSingleChoiceItems(
      cursor,
      checkedItem,
      labelColumn,
      listener
    ) as DialogBuilderM3
  }

  override fun setSingleChoiceItems(
    items: Array<CharSequence>?,
    checkedItem: Int,
    listener: DialogInterface.OnClickListener?
  ): DialogBuilderM3 {
    return super.setSingleChoiceItems(items, checkedItem, listener) as DialogBuilderM3
  }

  override fun setSingleChoiceItems(
    adapter: ListAdapter?,
    checkedItem: Int,
    listener: DialogInterface.OnClickListener?
  ): DialogBuilderM3 {
    return super.setSingleChoiceItems(adapter, checkedItem, listener) as DialogBuilderM3
  }

  override fun setOnItemSelectedListener(
    listener: AdapterView.OnItemSelectedListener?
  ): DialogBuilderM3 {
    return super.setOnItemSelectedListener(listener) as DialogBuilderM3
  }

  override fun setView(layoutResId: Int): DialogBuilderM3 {
    return super.setView(layoutResId) as DialogBuilderM3
  }

  override fun setView(view: View?): DialogBuilderM3 {
    return super.setView(view) as DialogBuilderM3
  }

  companion object {
    @AttrRes
    private val DEF_STYLE_ATTR = com.google.android.material.R.attr.alertDialogStyle

    @StyleRes
    private val DEF_STYLE_RES =
      com.google.android.material.R.style.MaterialAlertDialog_MaterialComponents

    @AttrRes
    private val MATERIAL_ALERT_DIALOG_THEME_OVERLAY =
      com.google.android.material.R.attr.materialAlertDialogTheme

    private fun getMaterialAlertDialogThemeOverlay(context: Context): Int {
      val materialAlertDialogThemeOverlay = MaterialAttributes.resolve(
        context,
        MATERIAL_ALERT_DIALOG_THEME_OVERLAY
      ) ?: return 0
      return materialAlertDialogThemeOverlay.data
    }

    private fun createMaterialAlertDialogThemedContext(context: Context): Context {
      val themeOverlayId = getMaterialAlertDialogThemeOverlay(context)
      val themedContext =
        MaterialThemeOverlay.wrap(context, null, DEF_STYLE_ATTR, DEF_STYLE_RES)
      return if (themeOverlayId == 0) {
        themedContext
      } else ContextThemeWrapper(
        themedContext,
        themeOverlayId
      )
    }

    private fun getOverridingThemeResId(context: Context, overrideThemeResId: Int): Int {
      return if (overrideThemeResId == 0) getMaterialAlertDialogThemeOverlay(context) else overrideThemeResId
    }
  }
}
