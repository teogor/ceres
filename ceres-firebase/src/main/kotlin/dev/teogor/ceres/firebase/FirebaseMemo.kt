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

package dev.teogor.ceres.firebase

import androidx.annotation.NonNull
import com.zeoflow.memo.Memo
import com.zeoflow.memo.NoEncryption
import com.zeoflow.memo.common.MemoEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@MemoEntity
class FirebaseMemo @Inject constructor() {

  init {
    Memo.init()
      .withEncryption(NoEncryption())
      .build()
  }

  @NonNull
  var signatureChecked = false
    get() {
      return Memo["signatureChecked", field]
    }
    set(`value`) {
      Memo.put("signatureChecked", value)
    }

  @NonNull
  var hasValidSignature = false
    get() = Memo["hasValidSignature", field]
    set(`value`) {
      Memo.put("hasValidSignature", value)
    }

  @NonNull
  var userID = UserID()
    get() {
      val value = Memo["userID", field]
      if (value.isEmpty()) {
        field.value = value.generate()
        Memo.put("userID", field)
        return field
      }
      return value
    }
    set(`value`) {
      Memo.put("userID", value)
    }
}
