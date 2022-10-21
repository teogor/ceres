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

package dev.teogor.ceres.m3.theme

import com.zeoflow.memo.common.Default
import com.zeoflow.memo.common.DefaultType
import com.zeoflow.memo.common.Hilt
import com.zeoflow.memo.common.MemoEntity
import dev.teogor.ceres.components.annotation.ColorString

@MemoEntity("ThemeMemo")
@Hilt
data class ThemeStore(
  @ColorString @Default(StringDefault::class)
  val seed: String,
  @Default(AppThemeDefault::class) val appTheme: AppThemeType,
  @Default(JustBlackThemeDefault::class) val justBlackTheme: JustBlackThemeType,
  @Default(FalseBooleanDefault::class) val materialYouEnabled: Boolean,
  @Default(TrueBooleanDefault::class) val desaturatedColoursEnabled: Boolean,
  @Default(FalseBooleanDefault::class) val colouredBackgroundEnabled: Boolean
)

class StringDefault : DefaultType<String> {
  @ColorString
  override fun value(): String {
    return "#0B57D0"
  }
}

class FalseBooleanDefault : DefaultType<Boolean> {
  override fun value(): Boolean {
    return false
  }
}

class TrueBooleanDefault : DefaultType<Boolean> {
  override fun value(): Boolean {
    return true
  }
}

class AppThemeDefault : DefaultType<AppThemeType> {
  override fun value(): AppThemeType {
    return AppThemeType.FollowSystem
  }
}

class JustBlackThemeDefault : DefaultType<JustBlackThemeType> {
  override fun value(): JustBlackThemeType {
    return JustBlackThemeType.Off
  }
}
