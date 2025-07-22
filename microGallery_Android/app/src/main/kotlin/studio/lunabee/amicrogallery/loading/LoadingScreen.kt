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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import org.koin.compose.koinInject
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.colors
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.spacing
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.typography
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.amicrogallery.bottomBar.BottomBarViewModel

@Composable
fun LoadingScreen(loadingUiState: LoadingUiState) {
    val bottomBarViewModel = koinInject<BottomBarViewModel>()
    LaunchedEffect(Unit) { bottomBarViewModel.set(false) }
    when (loadingUiState) {
        is LoadingUiState.Error -> ShowError(loadingUiState)
        is LoadingUiState.Fetching -> WaitingForResponse(loadingUiState)
    }
}

@Composable
fun WaitingForResponse(uiState: LoadingUiState.Fetching) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(
                text = stringResource(R.string.waitingForData),
                style = typography.header,
            )
            Text(
                text = stringResource(R.string.notLong),
                style = typography.title,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
            LinearProgressIndicator(
                color = colors.main,
                trackColor = colors.second.copy(alpha = 0.5f),
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
            uiState.years.forEach {
                Text(
                    text = stringResource(R.string.year_found, it),
                )
            }
        }
    }
}

@Composable
fun ShowError(uiState: LoadingUiState.Error) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.errorOccured),
                style = typography.header,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Text(
                text = uiState.errorMessage ?: "",
                style = typography.title,
                modifier = Modifier.padding(spacing.SpacingSmall),
            )
            Button(
                onClick = uiState.reload,
            ) {
                Text(text = stringResource(R.string.reload))
            }
        }
    }
}
