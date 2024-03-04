/*
 * Copyright 2024 teogor (Teodor Grigor)
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

package dev.teogor.ceres.screen.ui.res

import androidx.compose.runtime.Composable
import dev.teogor.ceres.core.foundation.compositions.quantityStringResource
import dev.teogor.ceres.core.foundation.compositions.stringArrayResource
import dev.teogor.ceres.core.foundation.compositions.stringResource
import dev.teogor.ceres.screen.ui.R
import kotlin.Any
import kotlin.Int
import kotlin.reflect.KProperty

public enum class ResourceKey(
  public val id: Int,
  public val type: ResourceType,
) {
  ABOUT(R.string.about, ResourceType.String),
  ABOUT_US(R.string.about_us, ResourceType.String),
  AND(R.string.and, ResourceType.String),
  APK_SIGNATURE(R.string.apk_signature, ResourceType.String),
  APP_INFO(R.string.app_info, ResourceType.String),
  APP_LICENSE(R.string.app_license, ResourceType.String),
  APP_THEME(R.array.app_theme, ResourceType.Array),
  APP_VERSION(R.string.app_version, ResourceType.String),
  BACKUP_AND_RESTORE(R.string.backup_and_restore, ResourceType.String),
  BUILD_DATE(R.string.build_date, ResourceType.String),
  BUILD_HASH(R.string.build_hash, ResourceType.String),
  BUILT_WITH_CERES(R.string.built_with_ceres, ResourceType.String),
  BUSINESS(R.string.business, ResourceType.String),
  CANCEL(R.string.cancel, ResourceType.String),
  CERES_FRAMEWORK_VERSION(R.string.ceres_framework_version, ResourceType.String),
  COMPANY(R.string.company, ResourceType.String),
  COPYRIGHT_ALL_RIGHTS_RESERVED(R.string.copyright_all_rights_reserved, ResourceType.String),
  COPYRIGHT_POLICY(R.string.copyright_policy, ResourceType.String),
  CORPORATION(R.string.corporation, ResourceType.String),
  DATA_AND_PRIVACY(R.string.data_and_privacy, ResourceType.String),
  DESIGN_AND_COLOR_OPTIONS(R.string.design_and_color_options, ResourceType.String),
  DEVELOPER(R.string.developer, ResourceType.String),
  ENTERPRISE(R.string.enterprise, ResourceType.String),
  FEEDBACK(R.string.feedback, ResourceType.String),
  FULL_BACKUP_OF_YOUR_APP(R.string.full_backup_of_your_app, ResourceType.String),
  HELP_AND_FEEDBACK(R.string.help_and_feedback, ResourceType.String),
  ID(R.string.id, ResourceType.String),
  JUST_BLACK(R.array.just_black, ResourceType.Array),
  LANGUAGE(R.string.language, ResourceType.String),
  LEGAL_AGREEMENTS_HEADER(R.string.legal_agreements_header, ResourceType.String),
  LICENSE_DETAILS_FOR_OPEN_SOURCE_SOFTWARE(
    R.string.license_details_for_open_source_software,
    ResourceType.String,
  ),
  LICENSED_UNDER(R.string.licensed_under, ResourceType.String),
  LICENSES(R.string.licenses, ResourceType.String),
  LICENSES_ARRAY(R.array.licenses_array, ResourceType.Array),
  LOOK_AND_FEEL(R.string.look_and_feel, ResourceType.String),
  LOOK_AND_FEEL_APP_COLOR_THEME(R.string.look_and_feel_app_color_theme, ResourceType.String),
  LOOK_AND_FEEL_APP_COLOR_THEME_SUBTITLE(
    R.string.look_and_feel_app_color_theme_subtitle,
    ResourceType.String,
  ),
  LOOK_AND_FEEL_APP_THEME(R.string.look_and_feel_app_theme, ResourceType.String),
  LOOK_AND_FEEL_APP_THEME_SUBTITLE(R.string.look_and_feel_app_theme_subtitle, ResourceType.String),
  LOOK_AND_FEEL_DYNAMIC_THEMING(R.string.look_and_feel_dynamic_theming, ResourceType.String),
  LOOK_AND_FEEL_DYNAMIC_THEMING_SUBTITLE(
    R.string.look_and_feel_dynamic_theming_subtitle,
    ResourceType.String,
  ),
  LOOK_AND_FEEL_HEADER_APPEARANCE(R.string.look_and_feel_header_appearance, ResourceType.String),
  LOOK_AND_FEEL_JUST_BLACK(R.string.look_and_feel_just_black, ResourceType.String),
  LOOK_AND_FEEL_JUST_BLACK_SUBTITLE(
    R.string.look_and_feel_just_black_subtitle,
    ResourceType.String,
  ),
  LOOK_AND_FEEL_SOUND_FEEDBACK(R.string.look_and_feel_sound_feedback, ResourceType.String),
  LOOK_AND_FEEL_SOUND_FEEDBACK_SUBTITLE(
    R.string.look_and_feel_sound_feedback_subtitle,
    ResourceType.String,
  ),
  LOOK_AND_FEEL_VIBRATION_FEEDBACK(R.string.look_and_feel_vibration_feedback, ResourceType.String),
  LOOK_AND_FEEL_VIBRATION_FEEDBACK_SUBTITLE(
    R.string.look_and_feel_vibration_feedback_subtitle,
    ResourceType.String,
  ),
  MADE_IN(R.string.made_in, ResourceType.String),
  MANAGE_YOUR_DATA_AND_PRIVACY_PREFERENCES(
    R.string.manage_your_data_and_privacy_preferences,
    ResourceType.String,
  ),
  OPEN_SOURCE_LICENSES(R.string.open_source_licenses, ResourceType.String),
  ORGANIZATION(R.string.organization, ResourceType.String),
  PRIVACY_OPTIONS(R.string.privacy_options, ResourceType.String),
  PRIVACY_POLICY(R.string.privacy_policy, ResourceType.String),
  REFUND_POLICY(R.string.refund_policy, ResourceType.String),
  RESET(R.string.reset, ResourceType.String),
  RESET_ADS_CONSENT(R.string.reset_ads_consent, ResourceType.String),
  RESET_ADS_CONSENT_DIALOG_TEXT(R.string.reset_ads_consent_dialog_text, ResourceType.String),
  RESET_ADS_CONSENT_DIALOG_TITLE(R.string.reset_ads_consent_dialog_title, ResourceType.String),
  RESET_ADS_CONSENT_SUBTITLE(R.string.reset_ads_consent_subtitle, ResourceType.String),
  RESET_ONBOARDING(R.string.reset_onboarding, ResourceType.String),
  RESET_ONBOARDING_DIALOG_CANCEL_BUTTON(
    R.string.reset_onboarding_dialog_cancel_button,
    ResourceType.String,
  ),
  RESET_ONBOARDING_DIALOG_DELETE_USER_STORED_CONTENT(
    R.string.reset_onboarding_dialog_delete_user_stored_content,
    ResourceType.String,
  ),
  RESET_ONBOARDING_DIALOG_RESTART_BUTTON(
    R.string.reset_onboarding_dialog_restart_button,
    ResourceType.String,
  ),
  RESET_ONBOARDING_DIALOG_TEXT(R.string.reset_onboarding_dialog_text, ResourceType.String),
  RESET_ONBOARDING_DIALOG_TITLE(R.string.reset_onboarding_dialog_title, ResourceType.String),
  RESET_ONBOARDING_SUBTITLE(R.string.reset_onboarding_subtitle, ResourceType.String),
  RESTART(R.string.restart, ResourceType.String),
  REVIEW_OUR_COPYRIGHT_POLICY(R.string.review_our_copyright_policy, ResourceType.String),
  REVIEW_OUR_PRIVACY_POLICY(R.string.review_our_privacy_policy, ResourceType.String),
  REVIEW_OUR_REFUND_POLICY(R.string.review_our_refund_policy, ResourceType.String),
  REVIEW_OUR_TERMS_OF_SERVICE(R.string.review_our_terms_of_service, ResourceType.String),
  SECURITY_PATCH(R.string.security_patch, ResourceType.String),
  SETTINGS(R.string.settings, ResourceType.String),
  SHOW_LESS(R.string.show_less, ResourceType.String),
  SHOW_MORE(R.string.show_more, ResourceType.String),
  SYSTEM(R.string.system, ResourceType.String),
  TERMS_OF_SERVICE(R.string.terms_of_service, ResourceType.String),
  THIS_ACTION_CANNOT_BE_UNDONE(R.string.this_action_cannot_be_undone, ResourceType.String),
  UI(R.string.ui, ResourceType.String),
  USER_ID(R.string.user_id, ResourceType.String),
  USER_ID_SUBTITLE(R.string.user_id_subtitle, ResourceType.String),
  VERSION_INFO(R.string.version_info, ResourceType.String),
}

public operator fun ResourceKey.getValue(thisObj: Any?, `property`: KProperty<*>): Int = id

@Composable
public inline fun <reified Type> ResourceKey.asResource(quantity: Int = 0, vararg args: Any): Type =
  when (type) {
    ResourceType.Array -> stringArrayResource(id) as Type
    ResourceType.Plurals -> quantityStringResource(id, quantity, *args) as Type
    ResourceType.String -> stringResource(id, *args) as Type
  }
