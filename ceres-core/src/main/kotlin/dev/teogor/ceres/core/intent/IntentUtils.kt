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

package dev.teogor.ceres.core.intent

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import com.zeoflow.startup.ktx.ApplicationInitializer

object AppDetailsSettingsIntent : IntentType {
  override fun call() {
    val context = ApplicationInitializer.context
    val intent = Intent()
    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    val uri: Uri = Uri.fromParts("package", context.packageName, null)
    intent.data = uri
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
  }
}

object AppNotificationSettingsIntent : IntentType {
  override fun call() {
    val context = ApplicationInitializer.context
    val intent = Intent()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
      intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
    } else {
      intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
      intent.putExtra("app_package", context.packageName)
      intent.putExtra("app_uid", context.applicationInfo.uid)
    }
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
  }
}
