package studio.lunabee.amicrogallery.settings.entries

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.typography
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.amicrogallery.settings.SettingsUiState

@Composable
fun CacheSettingsEntry(modifier: Modifier = Modifier, uiState: SettingsUiState) {
    val context = LocalContext.current
    Text(
        text = stringResource(R.string.cache_and_settings),
        style = typography.title,
    )
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(
            onClick = uiState.resetData,
        ) {
            Text(
                text = stringResource(R.string.reset_data),
                style = typography.body,
            )
        }

        Button(
            onClick = { uiState.clearCache(context) },
        ) {
            Text(
                text = stringResource(R.string.empty_cache),
                style = typography.body,
            )
        }
    }
}
