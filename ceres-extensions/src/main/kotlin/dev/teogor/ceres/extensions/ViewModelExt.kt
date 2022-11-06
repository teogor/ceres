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

@file:JvmName("ViewModelExt")

package dev.teogor.ceres.extensions

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import kotlin.reflect.KClass

fun ViewModel.launchActivity(
  context: Context,
  activityClass: KClass<*>,
  flags: Int = 0,
  intentTransformer: Intent.() -> Unit = {}
) {
  context.launchActivity(
    activityClass = activityClass.java,
    flags = flags,
    intentTransformer = intentTransformer
  )
}

fun ViewModel.launchActivity(
  context: Context,
  activityClass: Class<*>,
  flags: Int = 0,
  intentTransformer: Intent.() -> Unit = {}
) {
  context.launchActivity(
    activityClass = activityClass,
    flags = flags,
    intentTransformer = intentTransformer
  )
}
