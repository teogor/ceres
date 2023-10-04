package dev.teogor.ceres.screen.ui.onboarding.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.teogor.ceres.screen.core.layout.LayoutWithBottomHeader
import dev.teogor.ceres.ui.designsystem.Button
import dev.teogor.ceres.ui.designsystem.HorizontalDivider
import dev.teogor.ceres.ui.designsystem.OutlinedButton
import dev.teogor.ceres.ui.designsystem.Text
import dev.teogor.ceres.ui.theme.MaterialTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BoxScope.AdChoicesScreen(
  onBack: () -> Unit,
  onNext: () -> Unit,
) {
  // val networkConnectivity = LocalNetworkConnectivity.current
  // val state by remember { ConsentManager.state }
  // val canRequestAds: Boolean = remember(state) {
  //   when (val consentResult = state) {
  //     is ConsentResult.ConsentFormAcquired -> consentResult.canRequestAds
  //     is ConsentResult.ConsentFormDismissed -> consentResult.canRequestAds
  //     else -> false
  //   }
  // }
  // // todo state when consent is shown
  // LaunchedEffect(state, canRequestAds, networkConnectivity.isOffline) {
  //   if (canRequestAds || networkConnectivity.isOffline) {
  //     onNext()
  //   } else {
  //     ConsentManager.loadAndShowConsentFormIfRequired()
  //   }
  // }

  LayoutWithBottomHeader(
    hasScrollbar = false,
    bottomContent = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        if(!true) {
          LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
          )
        } else {
          HorizontalDivider(
            modifier = Modifier.padding(bottom = 10.dp),
            thickness = 1.dp,
          )
        }

        Spacer(modifier = Modifier.height(15.dp))

        Row(
          modifier = Modifier.padding(horizontal = 20.dp),
        ) {
          OutlinedButton(
            onClick = onBack,
          ) {
            Text(
              text = "Back",
              modifier = Modifier
                .padding(end = 10.dp, start = 10.dp),
            )
          }
          Spacer(modifier = Modifier.weight(1f))
          Button(
            onClick = onNext,
          ) {
            Text(
              text = "Next",
              modifier = Modifier
                .padding(end = 10.dp, start = 10.dp),
            )
          }
        }

        Spacer(modifier = Modifier.height(8.dp))
      }
    },
  ) {
    stickyHeader {
      HorizontalDivider(
        thickness = 1.dp,
      )
    }
  }
}
