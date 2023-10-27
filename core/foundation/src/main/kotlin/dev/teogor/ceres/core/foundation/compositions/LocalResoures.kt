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

package dev.teogor.ceres.core.foundation.compositions

import android.content.res.AssetFileDescriptor
import android.content.res.TypedArray
import android.content.res.XmlResourceParser
import android.os.Build
import android.util.TypedValue
import androidx.annotation.AnimRes
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
import androidx.annotation.StringRes
import androidx.annotation.XmlRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import dev.teogor.ceres.core.foundation.Resources
import dev.teogor.ceres.core.foundation.utils.errorCompositionLocal
import java.io.InputStream

val LocalResources = compositionLocalOf<Resources> {
  errorCompositionLocal("Resources")
}

@Composable
fun stringResource(@StringRes id: Int): String {
  val resources = LocalResources.current
  return remember(id) {
    resources.getString(id)
  }
}

@Composable
fun stringResource(@StringRes id: Int, vararg formatArgs: Any): String {
  val resources = LocalResources.current
  return remember(id, *formatArgs) {
    resources.getString(id, *formatArgs)
  }
}

@Composable
fun stringArrayResource(@ArrayRes id: Int): Array<String> {
  val resources = LocalResources.current
  return remember(id) {
    resources.getStringArray(id)
  }
}

@Composable
fun textResource(@StringRes id: Int): CharSequence {
  val resources = LocalResources.current
  return remember(id) {
    resources.getText(id)
  }
}

@Composable
fun textResource(@StringRes id: Int, def: CharSequence): CharSequence {
  val resources = LocalResources.current
  return remember(id) {
    resources.getText(id, def)
  }
}

@Composable
fun quantityTextResource(@PluralsRes id: Int, quantity: Int): CharSequence {
  val resources = LocalResources.current
  return remember(id) {
    resources.getQuantityText(id, quantity)
  }
}

@Composable
fun quantityStringResource(@PluralsRes id: Int, quantity: Int, vararg formatArgs: Any): String {
  val resources = LocalResources.current
  return remember(id) {
    resources.getQuantityString(id, quantity, *formatArgs)
  }
}

@Composable
fun quantityStringResource(@PluralsRes id: Int, quantity: Int): String {
  val resources = LocalResources.current
  return remember(id) {
    resources.getQuantityString(id, quantity)
  }
}

@Composable
fun textArrayResource(@ArrayRes id: Int): Array<CharSequence> {
  val resources = LocalResources.current
  return remember(id) {
    resources.getTextArray(id)
  }
}

@Composable
fun intArrayResource(@ArrayRes id: Int): IntArray {
  val resources = LocalResources.current
  return remember(id) {
    resources.getIntArray(id)
  }
}

@Composable
fun typedArrayResource(@ArrayRes id: Int): TypedArray {
  val resources = LocalResources.current
  return remember(id) {
    resources.obtainTypedArray(id)
  }
}

@Composable
fun dimensionResource(@DimenRes id: Int): Float {
  val resources = LocalResources.current
  return remember(id) {
    resources.getDimension(id)
  }
}

@Composable
fun dimensionPixelOffsetResource(@DimenRes id: Int): Int {
  val resources = LocalResources.current
  return remember(id) {
    resources.getDimensionPixelOffset(id)
  }
}

@Composable
fun dimensionPixelSizeResource(@DimenRes id: Int): Int {
  val resources = LocalResources.current
  return remember(id) {
    resources.getDimensionPixelSize(id)
  }
}

@Composable
fun fractionResource(@FractionRes id: Int, base: Int, pbase: Int): Float {
  val resources = LocalResources.current
  return remember(id) {
    resources.getFraction(id, base, pbase)
  }
}

@Composable
fun colorResource(@ColorRes id: Int): Int {
  val resources = LocalResources.current
  return remember(id) {
    resources.getColor(id)
  }
}

@Composable
fun booleanResource(@BoolRes id: Int): Boolean {
  val resources = LocalResources.current
  return remember(id) {
    resources.getBoolean(id)
  }
}

@Composable
fun integerResource(@IntegerRes id: Int): Int {
  val resources = LocalResources.current
  return remember(id) {
    resources.getInteger(id)
  }
}

@Composable
fun floatResource(@DimenRes id: Int): Float {
  val resources = LocalResources.current
  return remember(id) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
      resources.getFloat(id)
    } else {
      TypedValue().apply {
        resources.getValue(id, this, true)
      }.float
    }
  }
}

@Composable
fun layoutResource(@LayoutRes id: Int): XmlResourceParser {
  val resources = LocalResources.current
  return remember(id) {
    resources.getLayout(id)
  }
}

@Composable
fun animationResource(@AnimRes id: Int): XmlResourceParser {
  val resources = LocalResources.current
  return remember(id) {
    resources.getAnimation(id)
  }
}

@Composable
fun xmlResource(@XmlRes id: Int): XmlResourceParser {
  val resources = LocalResources.current
  return remember(id) {
    resources.getXml(id)
  }
}

@Composable
fun rawResource(@RawRes id: Int): InputStream {
  val resources = LocalResources.current
  return remember(id) {
    resources.openRawResource(id)
  }
}

@Composable
fun rawResource(@RawRes id: Int, value: TypedValue?): InputStream {
  val resources = LocalResources.current
  return remember(id) {
    resources.openRawResource(id, value)
  }
}

@Composable
fun rawResourceFd(@RawRes id: Int): AssetFileDescriptor {
  val resources = LocalResources.current
  return remember(id) {
    resources.openRawResourceFd(id)
  }
}

@Composable
fun ValueResource(@AnyRes id: Int, outValue: TypedValue?, resolveRefs: Boolean) {
  val resources = LocalResources.current
  LaunchedEffect(id, resolveRefs) {
    resources.getValue(id, outValue, resolveRefs)
  }
}

@Composable
fun ValueForDensityResource(
  @AnyRes id: Int,
  density: Int,
  outValue: TypedValue?,
  resolveRefs: Boolean,
) {
  val resources = LocalResources.current
  LaunchedEffect(id, resolveRefs) {
    resources.getValueForDensity(id, density, outValue, resolveRefs)
  }
}

@Composable
fun ValueResource(name: String?, outValue: TypedValue?, resolveRefs: Boolean) {
  val resources = LocalResources.current
  LaunchedEffect(name, resolveRefs) {
    resources.getValue(name, outValue, resolveRefs)
  }
}

@Composable
fun identifierResource(name: String?, defType: String?, defPackage: String?): Int {
  val resources = LocalResources.current
  return remember(name) {
    resources.getIdentifier(name, defType, defPackage)
  }
}

@Composable
fun resourceNameResource(@AnyRes resid: Int): String {
  val resources = LocalResources.current
  return remember(resid) {
    resources.getResourceName(resid)
  }
}

@Composable
fun resourcePackageNameResource(@AnyRes resid: Int): String {
  val resources = LocalResources.current
  return remember(resid) {
    resources.getResourcePackageName(resid)
  }
}

@Composable
fun resourceTypeNameResource(@AnyRes resid: Int): String {
  val resources = LocalResources.current
  return remember(resid) {
    resources.getResourceTypeName(resid)
  }
}

@Composable
fun resourceEntryNameResource(@AnyRes resid: Int): String {
  val resources = LocalResources.current
  return remember(resid) {
    resources.getResourceEntryName(resid)
  }
}
