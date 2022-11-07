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

package dev.teogor.ceres.m3.app

import androidx.databinding.ViewDataBinding
import dev.teogor.ceres.components.app.BaseFragment
import dev.teogor.ceres.m3.annotation.M3
import dev.teogor.ceres.m3.theme.IThemeM3

/**
 * MVVM base fragment class that extends [BaseFragment]
 * and is [M3] aware.
 *
 * **Developer Guides**
 * > For information about how to implement this class and more, read the documentation.
 *
 * @see [BaseActivityM3]
 * @see [BaseMenuSheetDialogFragmentM3]
 * @see [BaseViewModelM3]
 * @see [ViewDataBinding]
 *
 * @param B the type of [ViewDataBinding].
 * @param VM the type of [BaseViewModelM3].
 *
 * @author teogor
 * @since 1.0.0
 */
@M3
abstract class BaseFragmentM3<B : ViewDataBinding, VM : BaseViewModelM3> :
  BaseFragment<B, VM>(), IThemeM3
