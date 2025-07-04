package studio.lunabee.amicrogallery.settings.entries

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.typography
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.amicrogallery.settings.SettingsUiState

@Composable
fun ServerStatisticsSettingsEntry(modifier: Modifier = Modifier, uiState: SettingsUiState.HasData) {
    val remoteStatus = uiState.remoteStatus
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = stringResource(R.string.server_statistics),
                style = typography.title,
                modifier = Modifier.align(alignment = Alignment.CenterVertically),
            )
            Button(
                onClick = uiState.getRemoteStatus,
            ) {
                Text(
                    text = stringResource(R.string.refresh),
                    style = typography.body,
                )
            }
        }
        if (remoteStatus == null) {
            Text(
                text = stringResource(R.string.waitingForData),
                style = typography.labelBold,
            )
        } else {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = stringResource(R.string.temperature),
                    style = typography.body,
                )
                Text(
                    text = stringResource(R.string.celcius, remoteStatus.temperature.toFloat() / 1000.0f),
                    style = typography.body,
                )
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = stringResource(R.string.quantity_of_pictures),
                    style = typography.body,
                )
                Text(
                    text = stringResource(R.string.nphotos, remoteStatus.quantityHighRes),
                    style = typography.body,
                )
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = stringResource(R.string.quantity_of_pictures_low_res),
                    style = typography.body,
                )
                Text(
                    text = stringResource(R.string.nphotos, remoteStatus.quantityLowRes),
                    style = typography.body,
                )
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = stringResource(if (remoteStatus.isPlugged) R.string.disk_is_plugged else R.string.disk_not_plugged),
                    style = typography.body,
                )
            }
        }
    }
}
