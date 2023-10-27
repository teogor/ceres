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

package dev.teogor.ceres.core.foundation

import android.annotation.SuppressLint
import android.content.res.AssetFileDescriptor
import android.content.res.TypedArray
import android.content.res.XmlResourceParser
import android.os.Build
import android.util.TypedValue
import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.annotation.AnyRes
import androidx.annotation.ArrayRes
import androidx.annotation.BoolRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.FractionRes
import androidx.annotation.IntegerRes
import androidx.annotation.LayoutRes
import androidx.annotation.PluralsRes
import androidx.annotation.RawRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.annotation.XmlRes
import java.io.InputStream

class DefaultResources(
  private val resources: android.content.res.Resources,
) : Resources {
  override fun getString(@StringRes id: Int): String {
    return resources.getString(id)
  }

  override fun getString(@StringRes id: Int, vararg formatArgs: Any): String {
    return resources.getString(id, *formatArgs)
  }

  override fun getStringArray(@ArrayRes id: Int): Array<String> {
    return resources.getStringArray(id)
  }

  override fun getText(@StringRes id: Int): CharSequence {
    return resources.getText(id)
  }

  override fun getText(@StringRes id: Int, def: CharSequence): CharSequence {
    return resources.getText(id, def)
  }

  override fun getQuantityText(@PluralsRes id: Int, quantity: Int): CharSequence {
    return resources.getQuantityText(id, quantity)
  }

  override fun getQuantityString(
    @PluralsRes id: Int,
    quantity: Int,
    vararg formatArgs: Any,
  ): String {
    return resources.getQuantityString(id, quantity, *formatArgs)
  }

  override fun getQuantityString(@PluralsRes id: Int, quantity: Int): String {
    return resources.getQuantityString(id, quantity)
  }

  override fun getTextArray(@ArrayRes id: Int): Array<CharSequence> {
    return resources.getTextArray(id)
  }

  override fun getIntArray(@ArrayRes id: Int): IntArray {
    return resources.getIntArray(id)
  }

  override fun obtainTypedArray(@ArrayRes id: Int): TypedArray {
    return resources.obtainTypedArray(id)
  }

  override fun getDimension(@DimenRes id: Int): Float {
    return resources.getDimension(id)
  }

  override fun getDimensionPixelOffset(@DimenRes id: Int): Int {
    return resources.getDimensionPixelOffset(id)
  }

  override fun getDimensionPixelSize(@DimenRes id: Int): Int {
    return resources.getDimensionPixelSize(id)
  }

  override fun getFraction(@FractionRes id: Int, base: Int, pbase: Int): Float {
    return resources.getFraction(id, base, pbase)
  }

  override fun getColor(@ColorRes id: Int): Int {
    return resources.getColor(id)
  }

  override fun getBoolean(@BoolRes id: Int): Boolean {
    return resources.getBoolean(id)
  }

  override fun getInteger(@IntegerRes id: Int): Int {
    return resources.getInteger(id)
  }

  @RequiresApi(Build.VERSION_CODES.Q)
  override fun getFloat(@DimenRes id: Int): Float {
    return resources.getFloat(id)
  }

  override fun getLayout(@LayoutRes id: Int): XmlResourceParser {
    return resources.getLayout(id)
  }

  override fun getAnimation(@AnimatorRes @AnimRes id: Int): XmlResourceParser {
    return resources.getAnimation(id)
  }

  override fun getXml(@XmlRes id: Int): XmlResourceParser {
    return resources.getXml(id)
  }

  override fun openRawResource(@RawRes id: Int): InputStream {
    return resources.openRawResource(id)
  }

  override fun openRawResource(@RawRes id: Int, value: TypedValue?): InputStream {
    return resources.openRawResource(id, value)
  }

  override fun openRawResourceFd(@RawRes id: Int): AssetFileDescriptor {
    return resources.openRawResourceFd(id)
  }

  override fun getValue(@AnyRes id: Int, outValue: TypedValue?, resolveRefs: Boolean) {
    resources.getValue(id, outValue, resolveRefs)
  }

  override fun getValueForDensity(
    @AnyRes id: Int,
    density: Int,
    outValue: TypedValue?,
    resolveRefs: Boolean,
  ) {
    resources.getValueForDensity(id, density, outValue, resolveRefs)
  }

  override fun getValue(name: String?, outValue: TypedValue?, resolveRefs: Boolean) {
    resources.getValue(name, outValue, resolveRefs)
  }

  @SuppressLint("DiscouragedApi")
  override fun getIdentifier(name: String?, defType: String?, defPackage: String?): Int {
    return resources.getIdentifier(name, defType, defPackage)
  }

  override fun getResourceName(@AnyRes resid: Int): String {
    return resources.getResourceName(resid)
  }

  override fun getResourcePackageName(@AnyRes resid: Int): String {
    return resources.getResourcePackageName(resid)
  }

  override fun getResourceTypeName(@AnyRes resid: Int): String {
    return resources.getResourceTypeName(resid)
  }

  override fun getResourceEntryName(@AnyRes resid: Int): String {
    return resources.getResourceEntryName(resid)
  }
}

interface Resources {
  fun getString(@StringRes id: Int): String
  fun getString(@StringRes id: Int, vararg formatArgs: Any): String
  fun getStringArray(@ArrayRes id: Int): Array<String>
  fun getText(@StringRes id: Int): CharSequence
  fun getText(@StringRes id: Int, def: CharSequence): CharSequence
  fun getQuantityText(@PluralsRes id: Int, quantity: Int): CharSequence
  fun getQuantityString(@PluralsRes id: Int, quantity: Int, vararg formatArgs: Any): String
  fun getQuantityString(@PluralsRes id: Int, quantity: Int): String
  fun getTextArray(@ArrayRes id: Int): Array<CharSequence>
  fun getIntArray(@ArrayRes id: Int): IntArray
  fun obtainTypedArray(@ArrayRes id: Int): TypedArray
  fun getDimension(@DimenRes id: Int): Float
  fun getDimensionPixelOffset(@DimenRes id: Int): Int
  fun getDimensionPixelSize(@DimenRes id: Int): Int
  fun getFraction(@FractionRes id: Int, base: Int, pbase: Int): Float
  fun getColor(@ColorRes id: Int): Int
  fun getBoolean(@BoolRes id: Int): Boolean
  fun getInteger(@IntegerRes id: Int): Int

  @RequiresApi(Build.VERSION_CODES.Q)
  fun getFloat(@DimenRes id: Int): Float
  fun getLayout(@LayoutRes id: Int): XmlResourceParser
  fun getAnimation(@AnimatorRes @AnimRes id: Int): XmlResourceParser
  fun getXml(@XmlRes id: Int): XmlResourceParser
  fun openRawResource(@RawRes id: Int): InputStream
  fun openRawResource(@RawRes id: Int, value: TypedValue?): InputStream
  fun openRawResourceFd(@RawRes id: Int): AssetFileDescriptor
  fun getValue(@AnyRes id: Int, outValue: TypedValue?, resolveRefs: Boolean)
  fun getValueForDensity(@AnyRes id: Int, density: Int, outValue: TypedValue?, resolveRefs: Boolean)
  fun getValue(name: String?, outValue: TypedValue?, resolveRefs: Boolean)
  fun getIdentifier(name: String?, defType: String?, defPackage: String?): Int
  fun getResourceName(@AnyRes resid: Int): String
  fun getResourcePackageName(@AnyRes resid: Int): String
  fun getResourceTypeName(@AnyRes resid: Int): String
  fun getResourceEntryName(@AnyRes resid: Int): String
}
