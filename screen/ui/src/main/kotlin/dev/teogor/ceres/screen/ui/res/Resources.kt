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
import kotlin.Array
import kotlin.Int
import kotlin.String

public object Resources {
  public val About: String
    @Composable
    get() = ResourceKey.ABOUT.asResource()

  public val AboutUs: String
    @Composable
    get() = ResourceKey.ABOUT_US.asResource()

  public val And: String
    @Composable
    get() = ResourceKey.AND.asResource()

  public val ApkSignature: String
    @Composable
    get() = ResourceKey.APK_SIGNATURE.asResource()

  public val AppInfo: String
    @Composable
    get() = ResourceKey.APP_INFO.asResource()

  public val AppLicense: String
    @Composable
    get() = ResourceKey.APP_LICENSE.asResource()

  public val AppVersion: String
    @Composable
    get() = ResourceKey.APP_VERSION.asResource()

  public val BackupAndRestore: String
    @Composable
    get() = ResourceKey.BACKUP_AND_RESTORE.asResource()

  public val BuildDate: String
    @Composable
    get() = ResourceKey.BUILD_DATE.asResource()

  public val BuildHash: String
    @Composable
    get() = ResourceKey.BUILD_HASH.asResource()

  public val BuiltWithCeres: String
    @Composable
    get() = ResourceKey.BUILT_WITH_CERES.asResource()

  public val Business: String
    @Composable
    get() = ResourceKey.BUSINESS.asResource()

  public val Cancel: String
    @Composable
    get() = ResourceKey.CANCEL.asResource()

  public val CeresFrameworkVersion: String
    @Composable
    get() = ResourceKey.CERES_FRAMEWORK_VERSION.asResource()

  public val Company: String
    @Composable
    get() = ResourceKey.COMPANY.asResource()

  public val CopyrightPolicy: String
    @Composable
    get() = ResourceKey.COPYRIGHT_POLICY.asResource()

  public val Corporation: String
    @Composable
    get() = ResourceKey.CORPORATION.asResource()

  public val DataAndPrivacy: String
    @Composable
    get() = ResourceKey.DATA_AND_PRIVACY.asResource()

  public val DesignAndColorOptions: String
    @Composable
    get() = ResourceKey.DESIGN_AND_COLOR_OPTIONS.asResource()

  public val Developer: String
    @Composable
    get() = ResourceKey.DEVELOPER.asResource()

  public val Enterprise: String
    @Composable
    get() = ResourceKey.ENTERPRISE.asResource()

  public val Feedback: String
    @Composable
    get() = ResourceKey.FEEDBACK.asResource()

  public val FullBackupOfYourApp: String
    @Composable
    get() = ResourceKey.FULL_BACKUP_OF_YOUR_APP.asResource()

  public val HelpAndFeedback: String
    @Composable
    get() = ResourceKey.HELP_AND_FEEDBACK.asResource()

  public val Id: String
    @Composable
    get() = ResourceKey.ID.asResource()

  public val Language: String
    @Composable
    get() = ResourceKey.LANGUAGE.asResource()

  public val LegalAgreementsHeader: String
    @Composable
    get() = ResourceKey.LEGAL_AGREEMENTS_HEADER.asResource()

  public val LicenseDetailsForOpenSourceSoftware: String
    @Composable
    get() = ResourceKey.LICENSE_DETAILS_FOR_OPEN_SOURCE_SOFTWARE.asResource()

  public val Licenses: String
    @Composable
    get() = ResourceKey.LICENSES.asResource()

  public val LookAndFeel: String
    @Composable
    get() = ResourceKey.LOOK_AND_FEEL.asResource()

  public val LookAndFeelAppColorTheme: String
    @Composable
    get() = ResourceKey.LOOK_AND_FEEL_APP_COLOR_THEME.asResource()

  public val LookAndFeelAppColorThemeSubtitle: String
    @Composable
    get() = ResourceKey.LOOK_AND_FEEL_APP_COLOR_THEME_SUBTITLE.asResource()

  public val LookAndFeelAppTheme: String
    @Composable
    get() = ResourceKey.LOOK_AND_FEEL_APP_THEME.asResource()

  public val LookAndFeelAppThemeSubtitle: String
    @Composable
    get() = ResourceKey.LOOK_AND_FEEL_APP_THEME_SUBTITLE.asResource()

  public val LookAndFeelDynamicTheming: String
    @Composable
    get() = ResourceKey.LOOK_AND_FEEL_DYNAMIC_THEMING.asResource()

  public val LookAndFeelDynamicThemingSubtitle: String
    @Composable
    get() = ResourceKey.LOOK_AND_FEEL_DYNAMIC_THEMING_SUBTITLE.asResource()

  public val LookAndFeelHeaderAppearance: String
    @Composable
    get() = ResourceKey.LOOK_AND_FEEL_HEADER_APPEARANCE.asResource()

  public val LookAndFeelJustBlack: String
    @Composable
    get() = ResourceKey.LOOK_AND_FEEL_JUST_BLACK.asResource()

  public val LookAndFeelJustBlackSubtitle: String
    @Composable
    get() = ResourceKey.LOOK_AND_FEEL_JUST_BLACK_SUBTITLE.asResource()

  public val LookAndFeelSoundFeedback: String
    @Composable
    get() = ResourceKey.LOOK_AND_FEEL_SOUND_FEEDBACK.asResource()

  public val LookAndFeelSoundFeedbackSubtitle: String
    @Composable
    get() = ResourceKey.LOOK_AND_FEEL_SOUND_FEEDBACK_SUBTITLE.asResource()

  public val LookAndFeelVibrationFeedback: String
    @Composable
    get() = ResourceKey.LOOK_AND_FEEL_VIBRATION_FEEDBACK.asResource()

  public val LookAndFeelVibrationFeedbackSubtitle: String
    @Composable
    get() = ResourceKey.LOOK_AND_FEEL_VIBRATION_FEEDBACK_SUBTITLE.asResource()

  public val MadeIn: String
    @Composable
    get() = ResourceKey.MADE_IN.asResource()

  public val ManageYourDataAndPrivacyPreferences: String
    @Composable
    get() = ResourceKey.MANAGE_YOUR_DATA_AND_PRIVACY_PREFERENCES.asResource()

  public val OpenSourceLicenses: String
    @Composable
    get() = ResourceKey.OPEN_SOURCE_LICENSES.asResource()

  public val Organization: String
    @Composable
    get() = ResourceKey.ORGANIZATION.asResource()

  public val PrivacyOptions: String
    @Composable
    get() = ResourceKey.PRIVACY_OPTIONS.asResource()

  public val PrivacyPolicy: String
    @Composable
    get() = ResourceKey.PRIVACY_POLICY.asResource()

  public val RefundPolicy: String
    @Composable
    get() = ResourceKey.REFUND_POLICY.asResource()

  public val Reset: String
    @Composable
    get() = ResourceKey.RESET.asResource()

  public val ResetAdsConsent: String
    @Composable
    get() = ResourceKey.RESET_ADS_CONSENT.asResource()

  public val ResetAdsConsentDialogText: String
    @Composable
    get() = ResourceKey.RESET_ADS_CONSENT_DIALOG_TEXT.asResource()

  public val ResetAdsConsentDialogTitle: String
    @Composable
    get() = ResourceKey.RESET_ADS_CONSENT_DIALOG_TITLE.asResource()

  public val ResetAdsConsentSubtitle: String
    @Composable
    get() = ResourceKey.RESET_ADS_CONSENT_SUBTITLE.asResource()

  public val ResetOnboarding: String
    @Composable
    get() = ResourceKey.RESET_ONBOARDING.asResource()

  public val ResetOnboardingDialogCancelButton: String
    @Composable
    get() = ResourceKey.RESET_ONBOARDING_DIALOG_CANCEL_BUTTON.asResource()

  public val ResetOnboardingDialogDeleteUserStoredContent: String
    @Composable
    get() = ResourceKey.RESET_ONBOARDING_DIALOG_DELETE_USER_STORED_CONTENT.asResource()

  public val ResetOnboardingDialogRestartButton: String
    @Composable
    get() = ResourceKey.RESET_ONBOARDING_DIALOG_RESTART_BUTTON.asResource()

  public val ResetOnboardingDialogText: String
    @Composable
    get() = ResourceKey.RESET_ONBOARDING_DIALOG_TEXT.asResource()

  public val ResetOnboardingDialogTitle: String
    @Composable
    get() = ResourceKey.RESET_ONBOARDING_DIALOG_TITLE.asResource()

  public val ResetOnboardingSubtitle: String
    @Composable
    get() = ResourceKey.RESET_ONBOARDING_SUBTITLE.asResource()

  public val Restart: String
    @Composable
    get() = ResourceKey.RESTART.asResource()

  public val ReviewOurCopyrightPolicy: String
    @Composable
    get() = ResourceKey.REVIEW_OUR_COPYRIGHT_POLICY.asResource()

  public val ReviewOurPrivacyPolicy: String
    @Composable
    get() = ResourceKey.REVIEW_OUR_PRIVACY_POLICY.asResource()

  public val ReviewOurRefundPolicy: String
    @Composable
    get() = ResourceKey.REVIEW_OUR_REFUND_POLICY.asResource()

  public val ReviewOurTermsOfService: String
    @Composable
    get() = ResourceKey.REVIEW_OUR_TERMS_OF_SERVICE.asResource()

  public val SecurityPatch: String
    @Composable
    get() = ResourceKey.SECURITY_PATCH.asResource()

  public val Settings: String
    @Composable
    get() = ResourceKey.SETTINGS.asResource()

  public val ShowLess: String
    @Composable
    get() = ResourceKey.SHOW_LESS.asResource()

  public val ShowMore: String
    @Composable
    get() = ResourceKey.SHOW_MORE.asResource()

  public val System: String
    @Composable
    get() = ResourceKey.SYSTEM.asResource()

  public val TermsOfService: String
    @Composable
    get() = ResourceKey.TERMS_OF_SERVICE.asResource()

  public val ThisActionCannotBeUndone: String
    @Composable
    get() = ResourceKey.THIS_ACTION_CANNOT_BE_UNDONE.asResource()

  public val Ui: String
    @Composable
    get() = ResourceKey.UI.asResource()

  public val UserId: String
    @Composable
    get() = ResourceKey.USER_ID.asResource()

  public val UserIdSubtitle: String
    @Composable
    get() = ResourceKey.USER_ID_SUBTITLE.asResource()

  public val VersionInfo: String
    @Composable
    get() = ResourceKey.VERSION_INFO.asResource()

  public val AppTheme: Array<String>
    @Composable
    get() = ResourceKey.APP_THEME.asResource()

  public val JustBlack: Array<String>
    @Composable
    get() = ResourceKey.JUST_BLACK.asResource()

  public val LicensesArray: Array<String>
    @Composable
    get() = ResourceKey.LICENSES_ARRAY.asResource()

  @Composable
  public fun CopyrightAllRightsReserved(year: Int, holder: String): String =
    ResourceKey.COPYRIGHT_ALL_RIGHTS_RESERVED.asResource(args = arrayOf(year, holder))

  @Composable
  public fun LicensedUnder(license: String): String = ResourceKey.LICENSED_UNDER.asResource(
    args =
    arrayOf(license),
  )
}
