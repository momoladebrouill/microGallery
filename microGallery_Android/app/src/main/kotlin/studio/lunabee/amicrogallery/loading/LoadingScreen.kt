package studio.lunabee.amicrogallery.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.colors
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.spacing
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.typography
import studio.lunabee.amicrogallery.app.R

@Composable
fun LoadingScreen(loadingUiState: LoadingUiState, onAction: (LoadingAction) -> Unit) {
    when (loadingUiState) {
        is LoadingUiState.Default -> WaitingForResponse()
        is LoadingUiState.Error -> ShowError(loadingUiState.errorMessage, onAction)
    }
}

@Composable
fun WaitingForResponse() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(
                text = stringResource(R.string.waitingForData),

                style = typography.header,

            )
            Text(
                text = stringResource(R.string.notLong),
                style = typography.body,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
            LinearProgressIndicator(
                color = colors.main,
                trackColor = colors.second.copy(alpha = 0.5f),
            )
        }
    }
}

@Composable
fun ShowError(error: String?, onAction: (LoadingAction) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.errorOccured),
                style = typography.header,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Text(
                text = error ?: "",
                style = typography.title,
                modifier = Modifier.padding(spacing.SpacingSmall),
            )
            Button(
                onClick = { onAction(LoadingAction.Reload) },
            ) {
                Text(text = stringResource(R.string.reload))
            }
        }
    }
}
