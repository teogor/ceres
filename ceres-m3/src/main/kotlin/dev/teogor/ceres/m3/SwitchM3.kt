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

package dev.teogor.ceres.m3

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.SwitchCompat
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.material.R
import com.google.android.material.drawable.DrawableUtils
import com.google.android.material.internal.ThemeEnforcement
import com.google.android.material.internal.ViewUtils
import com.google.android.material.theme.overlay.MaterialThemeOverlay
import dev.teogor.ceres.extensions.defaultResId
import dev.teogor.ceres.m3.theme.IThemeM3

class SwitchM3(
  var wrapContext: Context,
  attrs: AttributeSet?,
  defStyleAttr: Int
) : SwitchCompat(
  MaterialThemeOverlay.wrap(wrapContext, attrs, defStyleAttr, DEF_STYLE_RES),
  attrs,
  defStyleAttr
),
  IThemeM3 {

  private var thumbDrawable: Drawable?
  private var thumbIconDrawable: Drawable?
  private var trackDrawable: Drawable?
  private var trackDecorationDrawable: Drawable?
  private var thumbTintList: ColorStateList?
  private var thumbIconTintList: ColorStateList?
  private var thumbIconTintMode: PorterDuff.Mode
  private var trackTintList: ColorStateList?
  private var trackDecorationTintList: ColorStateList?
  private var trackDecorationTintMode: PorterDuff.Mode
  private lateinit var currentStateUnchecked: IntArray
  private lateinit var currentStateChecked: IntArray

  @JvmOverloads
  constructor(context: Context, attrs: AttributeSet? = null) : this(
    context,
    attrs,
    R.attr.materialSwitchStyle
  )

  init {
    // Ensure we are using the correctly themed context rather than the context that was passed in.
    wrapContext = context
    thumbDrawable = super.getThumbDrawable()
    thumbTintList = super.getThumbTintList()
    super.setThumbTintList(null) // Always use our custom tinting logic
    trackDrawable = super.getTrackDrawable()
    trackTintList = super.getTrackTintList()
    super.setTrackTintList(null) // Always use our custom tinting logic
    val attributes = ThemeEnforcement.obtainTintedStyledAttributes(
      wrapContext,
      attrs,
      R.styleable.MaterialSwitch,
      defStyleAttr,
      DEF_STYLE_RES
    )
    thumbIconDrawable = attributes.getDrawable(R.styleable.MaterialSwitch_thumbIcon)
    thumbIconTintList = attributes.getColorStateList(R.styleable.MaterialSwitch_thumbIconTint)
    thumbIconTintMode = ViewUtils.parseTintMode(
      attributes.getInt(R.styleable.MaterialSwitch_thumbIconTintMode, defaultResId),
      PorterDuff.Mode.SRC_IN
    )
    trackDecorationDrawable = attributes.getDrawable(
      R.styleable.MaterialSwitch_trackDecoration
    )
    trackDecorationTintList = attributes.getColorStateList(
      R.styleable.MaterialSwitch_trackDecorationTint
    )
    trackDecorationTintMode = ViewUtils.parseTintMode(
      attributes.getInt(R.styleable.MaterialSwitch_trackDecorationTintMode, defaultResId),
      PorterDuff.Mode.SRC_IN
    )
    attributes.recycle()
    setEnforceSwitchWidth(false)

    if (!isInEditMode) {
      onThemeChanged()
    }
  }

  override fun onThemeChanged() {
    super.onThemeChanged()

    colorScheme().apply {
      val switchThumbTintStates = arrayOf(
        intArrayOf(-android.R.attr.state_enabled, -android.R.attr.state_checked),
        intArrayOf(-android.R.attr.state_enabled, android.R.attr.state_checked),
        intArrayOf(android.R.attr.state_checked, android.R.attr.state_pressed),
        intArrayOf(android.R.attr.state_checked),
        intArrayOf(-android.R.attr.state_checked, android.R.attr.state_pressed),
        intArrayOf()
      )
      val switchThumbTintColors = intArrayOf(
        onSurface,
        surface,
        primaryContainer,
        onPrimary,
        onSurfaceVariant,
        outline
      )
      val switchTrackTintStates = arrayOf(
        intArrayOf(-android.R.attr.state_enabled, -android.R.attr.state_checked),
        intArrayOf(-android.R.attr.state_enabled, android.R.attr.state_checked),
        intArrayOf(android.R.attr.state_checked),
        intArrayOf()
      )
      // todo track surface-variant color to be based on primary with tone
      val switchTrackTintColors = intArrayOf(
        surfaceVariant,
        onSurface,
        primary,
        surfaceVariant
      )
      val switchTrackDecorationTintStates = arrayOf(
        intArrayOf(android.R.attr.state_checked),
        intArrayOf(-android.R.attr.state_enabled),
        intArrayOf()
      )
      val switchTrackDecorationTintColors = intArrayOf(
        transparent,
        onSurface,
        outline
      )

      // todo add alpha ???
      //  states created based on mtrl_switch_thumb_icon_tint.xml
      //  states created based on mtrl_switch_thumb_tint.xml
      //  states created based on mtrl_switch_track_decoration_tint.xml
      //  states created based on mtrl_switch_track_tint.xml
      thumbTintList = ColorStateList(switchThumbTintStates, switchThumbTintColors)
      trackTintList = ColorStateList(switchTrackTintStates, switchTrackTintColors)
      trackDecorationTintList =
        ColorStateList(switchTrackDecorationTintStates, switchTrackDecorationTintColors)

      // todo ripple color is wrong
      refreshThumbDrawable()
      refreshTrackDrawable()
    }
  }

  override fun invalidate() {
    updateDrawableTints()
    super.invalidate()
  }

  override fun onCreateDrawableState(extraSpace: Int): IntArray {
    val drawableState = super.onCreateDrawableState(extraSpace + 1)
    if (thumbIconDrawable != null) {
      mergeDrawableStates(drawableState, STATE_SET_WITH_ICON)
    }
    currentStateUnchecked = DrawableUtils.getUncheckedState(drawableState)
    currentStateChecked = DrawableUtils.getCheckedState(drawableState)
    return drawableState
  }

  override fun setThumbDrawable(drawable: Drawable?) {
    thumbDrawable = drawable
    refreshThumbDrawable()
  }

  override fun getThumbDrawable(): Drawable? {
    return thumbDrawable
  }

  override fun setThumbTintList(tintList: ColorStateList?) {
    thumbTintList = tintList
    refreshThumbDrawable()
  }

  override fun getThumbTintList(): ColorStateList? {
    return thumbTintList
  }

  override fun setThumbTintMode(tintMode: PorterDuff.Mode?) {
    super.setThumbTintMode(tintMode)
    refreshThumbDrawable()
  }

  /**
   * Sets the drawable used for the thumb icon that will be drawn upon the thumb.
   *
   * @param resId Resource ID of a thumb icon drawable
   * @attr ref com.google.android.material.R.styleable#MaterialSwitch_thumbIcon
   */
  fun setThumbIconResource(@DrawableRes resId: Int) {
    setThumbIconDrawable(AppCompatResources.getDrawable(context, resId))
  }

  /**
   * Sets the drawable used for the thumb icon that will be drawn upon the thumb.
   *
   * @param icon Thumb icon drawable
   * @attr ref com.google.android.material.R.styleable#MaterialSwitch_thumbIcon
   */
  fun setThumbIconDrawable(icon: Drawable?) {
    thumbIconDrawable = icon
    refreshThumbDrawable()
  }

  /**
   * Gets the drawable used for the thumb icon that will be drawn upon the thumb.
   *
   * @attr ref com.google.android.material.R.styleable#MaterialSwitch_thumbIcon
   */
  fun getThumbIconDrawable(): Drawable? {
    return thumbIconDrawable
  }

  /**
   * Applies a tint to the thumb icon drawable. Does not modify the current
   * tint mode, which is [Mode.SRC_IN] by default.
   *
   *
   * Subsequent calls to [.setThumbIconDrawable] will
   * automatically mutate the drawable and apply the specified tint and tint
   * mode using [DrawableCompat.setTintList].
   *
   * @param tintList the tint to apply, may be `null` to clear tint
   * @attr ref com.google.android.material.R.styleable#MaterialSwitch_thumbIconTint
   */
  fun setThumbIconTintList(tintList: ColorStateList?) {
    thumbIconTintList = tintList
    refreshThumbDrawable()
  }

  /**
   * Returns the tint applied to the thumb icon drawable
   *
   * @attr ref com.google.android.material.R.styleable#MaterialSwitch_thumbIconTint
   */
  fun getThumbIconTintList(): ColorStateList? {
    return thumbIconTintList
  }

  /**
   * Specifies the blending mode used to apply the tint specified by
   * [.setThumbIconTintList]} to the thumb icon drawable.
   * The default mode is [Mode.SRC_IN].
   *
   * @param tintMode the blending mode used to apply the tint
   * @attr ref com.google.android.material.R.styleable#MaterialSwitch_thumbIconTintMode
   */
  fun setThumbIconTintMode(tintMode: PorterDuff.Mode) {
    thumbIconTintMode = tintMode
    refreshThumbDrawable()
  }

  /**
   * Returns the blending mode used to apply the tint to the thumb icon drawable
   *
   * @attr ref com.google.android.material.R.styleable#MaterialSwitch_thumbIconTintMode
   */
  fun getThumbIconTintMode(): PorterDuff.Mode {
    return thumbIconTintMode
  }

  override fun setTrackDrawable(track: Drawable?) {
    trackDrawable = track
    refreshTrackDrawable()
  }

  override fun getTrackDrawable(): Drawable? {
    return trackDrawable
  }

  override fun setTrackTintList(tintList: ColorStateList?) {
    trackTintList = tintList
    refreshTrackDrawable()
  }

  override fun getTrackTintList(): ColorStateList? {
    return trackTintList
  }

  override fun setTrackTintMode(tintMode: PorterDuff.Mode?) {
    super.setTrackTintMode(tintMode)
    refreshTrackDrawable()
  }

  /**
   * Set the drawable used for the track decoration that will be drawn upon the track.
   *
   * @param resId Resource ID of a track decoration drawable
   * @attr ref com.google.android.material.R.styleable#MaterialSwitch_trackDecoration
   */
  fun setTrackDecorationResource(@DrawableRes resId: Int) {
    setTrackDecorationDrawable(AppCompatResources.getDrawable(context, resId))
  }

  /**
   * Set the drawable used for the track decoration that will be drawn upon the track.
   *
   * @param trackDecoration Track decoration drawable
   * @attr ref com.google.android.material.R.styleable#MaterialSwitch_trackDecoration
   */
  fun setTrackDecorationDrawable(trackDecoration: Drawable?) {
    trackDecorationDrawable = trackDecoration
    refreshTrackDrawable()
  }

  /**
   * Get the drawable used for the track decoration that will be drawn upon the track.
   *
   * @attr ref com.google.android.material.R.styleable#MaterialSwitch_trackDecoration
   */
  fun getTrackDecorationDrawable(): Drawable? {
    return trackDecorationDrawable
  }

  /**
   * Applies a tint to the track decoration drawable. Does not modify the current
   * tint mode, which is [Mode.SRC_IN] by default.
   *
   *
   * Subsequent calls to [.setTrackDecorationDrawable] will
   * automatically mutate the drawable and apply the specified tint and tint
   * mode using [DrawableCompat.setTintList].
   *
   * @param tintList the tint to apply, may be `null` to clear tint
   * @attr ref com.google.android.material.R.styleable#MaterialSwitch_trackDecorationTint
   */
  fun setTrackDecorationTintList(tintList: ColorStateList?) {
    trackDecorationTintList = tintList
    refreshTrackDrawable()
  }

  /**
   * Returns the tint applied to the track decoration drawable
   *
   * @attr ref com.google.android.material.R.styleable#MaterialSwitch_trackDecorationTint
   */
  fun getTrackDecorationTintList(): ColorStateList? {
    return trackDecorationTintList
  }

  /**
   * Specifies the blending mode used to apply the tint specified by
   * [.setTrackDecorationTintList]} to the track decoration drawable.
   * The default mode is [Mode.SRC_IN].
   *
   * @param tintMode the blending mode used to apply the tint
   * @attr ref com.google.android.material.R.styleable#MaterialSwitch_trackDecorationTintMode
   */
  fun setTrackDecorationTintMode(tintMode: PorterDuff.Mode) {
    trackDecorationTintMode = tintMode
    refreshTrackDrawable()
  }

  /**
   * Returns the blending mode used to apply the tint to the track decoration drawable
   *
   * @attr ref com.google.android.material.R.styleable#MaterialSwitch_trackDecorationTintMode
   */
  fun getTrackDecorationTintMode(): PorterDuff.Mode {
    return trackDecorationTintMode
  }

  private fun refreshThumbDrawable() {
    thumbDrawable = DrawableUtils.createTintableDrawableIfNeeded(
      thumbDrawable,
      thumbTintList,
      thumbTintMode
    )
    thumbIconDrawable = DrawableUtils.createTintableDrawableIfNeeded(
      thumbIconDrawable,
      thumbIconTintList,
      thumbIconTintMode
    )
    updateDrawableTints()
    super.setThumbDrawable(
      DrawableUtils.compositeTwoLayeredDrawable(
        thumbDrawable,
        thumbIconDrawable
      )
    )
    refreshDrawableState()
  }

  private fun refreshTrackDrawable() {
    trackDrawable = DrawableUtils.createTintableDrawableIfNeeded(
      trackDrawable,
      trackTintList,
      trackTintMode
    )
    trackDecorationDrawable = DrawableUtils.createTintableDrawableIfNeeded(
      trackDecorationDrawable,
      trackDecorationTintList,
      trackDecorationTintMode
    )
    updateDrawableTints()
    val finalTrackDrawable: Drawable? =
      if (trackDrawable != null && trackDecorationDrawable != null) {
        LayerDrawable(arrayOf(trackDrawable!!, trackDecorationDrawable!!))
      } else if (trackDrawable != null) {
        trackDrawable
      } else {
        trackDecorationDrawable
      }
    if (finalTrackDrawable != null) {
      switchMinWidth = finalTrackDrawable.intrinsicWidth
    }
    super.setTrackDrawable(finalTrackDrawable)
  }

  private fun updateDrawableTints() {
    if (thumbTintList == null && thumbIconTintList == null && trackTintList == null && trackDecorationTintList == null) {
      // Early return to avoid heavy operation.
      return
    }
    val thumbPosition = thumbPosition
    if (thumbTintList != null) {
      setInterpolatedDrawableTintIfPossible(
        thumbDrawable,
        thumbTintList,
        currentStateUnchecked,
        currentStateChecked,
        thumbPosition
      )
    }
    if (thumbIconTintList != null) {
      setInterpolatedDrawableTintIfPossible(
        thumbIconDrawable,
        thumbIconTintList,
        currentStateUnchecked,
        currentStateChecked,
        thumbPosition
      )
    }
    if (trackTintList != null) {
      setInterpolatedDrawableTintIfPossible(
        trackDrawable,
        trackTintList,
        currentStateUnchecked,
        currentStateChecked,
        thumbPosition
      )
    }
    if (trackDecorationTintList != null) {
      setInterpolatedDrawableTintIfPossible(
        trackDecorationDrawable,
        trackDecorationTintList,
        currentStateUnchecked,
        currentStateChecked,
        thumbPosition
      )
    }
  }

  private fun setInterpolatedDrawableTintIfPossible(
    drawable: Drawable?,
    tint: ColorStateList?,
    stateUnchecked: IntArray,
    stateChecked: IntArray,
    thumbPosition: Float
  ) {
    if (drawable == null || tint == null) {
      return
    }
    DrawableCompat.setTint(
      drawable,
      ColorUtils.blendARGB(
        tint.getColorForState(stateUnchecked, 0),
        tint.getColorForState(stateChecked, 0),
        thumbPosition
      )
    )
  }

  companion object {
    private val DEF_STYLE_RES = R.style.Widget_Material3_CompoundButton_MaterialSwitch
    private val STATE_SET_WITH_ICON = intArrayOf(R.attr.state_with_icon)

    /**
     * Tints the given drawable with the interpolated color according to the provided thumb position
     * between unchecked and checked states. The reference color in unchecked and checked states will
     * be retrieved from the given [ColorStateList] according to the provided states.
     */
  }
}
