package studio.lunabee.amicrogallery.settings.entries

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.typography
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.amicrogallery.settings.SettingsUiState

@Composable
fun ViewUntimedSettingsEntry(modifier: Modifier = Modifier, uiState: SettingsUiState) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.untimed_title),
            style = typography.title,
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(
                    text = stringResource(R.string.view_date_less),
                    style = typography.labelBold,
                )
                Text(
                    text = stringResource(R.string.pictures_without_date),
                    style = typography.body,
                )
            }
            Button(
                onClick = uiState.jumpUntimed,
            ) {
                Text(
                    text = stringResource(R.string.view_untimed),
                    style = typography.body,
                )
            }
        }
    }
}
