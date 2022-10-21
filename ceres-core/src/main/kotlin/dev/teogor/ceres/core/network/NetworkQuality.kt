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

package dev.teogor.ceres.core.network

import androidx.annotation.Keep

enum class NetworkQuality {
  /**
   * Connection not available.
   */
  @Keep
  NOT_AVAILABLE,

  /**
   * Bandwidth under 150 kbps.
   */
  @Keep
  POOR,

  /**
   * Bandwidth between 150 and 550 kbps.
   */
  @Keep
  MODERATE,

  /**
   * Bandwidth between 550 and 2000 kbps.
   */
  @Keep
  GOOD,

  /**
   * Bandwidth between 2000 and 4000 kbps.
   */
  @Keep
  STRONG,

  /**
   * EXCELLENT - Bandwidth over 4000 kbps.
   */
  @Keep
  EXCELLENT,

  /**
   * Placeholder for unknown bandwidth. This is the initial value and will stay at this value
   * if a bandwidth cannot be accurately found.
   */
  @Keep
  UNKNOWN
}
