package studio.lunabee.amicrogallery.settings.entries

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.colors
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.typography
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.amicrogallery.settings.SettingsUiState

@Composable
fun VisualiseSettingsEntry(modifier: Modifier = Modifier, uiState: SettingsUiState.HasData) {
    var viewInHD: Boolean by remember { mutableStateOf(uiState.data.viewInHD) }
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.visualisation_option),
            style = typography.title,
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(
                    text = stringResource(R.string.view_in_hd),
                    style = typography.labelBold,
                )
                Text(
                    text = stringResource(R.string.consumes_more),
                    style = typography.body,
                )
                if (!viewInHD) {
                    Text(
                        text = stringResource(R.string.only_low_res),
                        style = typography.body,
                        color = colors.second,
                    )
                }
            }
            Switch(
                modifier = Modifier.align(alignment = Alignment.CenterVertically),
                checked = viewInHD,
                onCheckedChange = {
                    viewInHD = !viewInHD
                    uiState.toggleViewInHD()
                },
            )
        }
    }
}
