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

package dev.teogor.ceres.ads

import androidx.annotation.Keep

enum class AdEvent {
  /**
   * Ad is currently loading
   */
  @Keep
  IS_LOADING,

  /**
   * Ad failed to load
   */
  @Keep
  FAILED_TO_LOAD,

  /**
   * Ad loaded
   */
  @Keep
  LOADED,

  /**
   * Ad impression
   */
  @Keep
  IMPRESSION,

  /**
   * Ad was clicked
   */
  @Keep
  CLICKED,

  /**
   * Ad was dismissed
   */
  @Keep
  DISMISSED,

  /**
   * Ad completed
   */
  @Keep
  COMPLETED,

  /**
   * Ad not completed
   */
  @Keep
  NOT_COMPLETED
}
