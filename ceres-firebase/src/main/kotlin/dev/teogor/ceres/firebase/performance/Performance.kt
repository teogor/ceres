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

package dev.teogor.ceres.firebase.performance

import com.google.firebase.perf.FirebasePerformance
import com.google.firebase.perf.metrics.Trace
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Performance @Inject constructor() {

  private val mFirebasePerformance = FirebasePerformance.getInstance()

  fun initialize() {
  }

  fun enablePerformance(enable: Boolean) {
    mFirebasePerformance.isPerformanceCollectionEnabled = enable
  }

  fun newTrace(name: String): Trace {
    return mFirebasePerformance.newTrace(name)
  }
}
