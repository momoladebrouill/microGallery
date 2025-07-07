package studio.lunabee.amicrogallery.settings.entries

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.colors
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.spacing
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.typography
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.amicrogallery.settings.SettingsUiState

@Composable
fun IPAddressesSettingsEntry(modifier: Modifier = Modifier, uiState: SettingsUiState) {
    val data = uiState.data

    val keyboardController = LocalSoftwareKeyboardController.current
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.adress_of_server),
            style = typography.title,
        )

        OutlinedTextField(
            value = data.ipv4,
            singleLine = true,
            shape = shapes.large,
            modifier = Modifier,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colors.background,
                unfocusedContainerColor = colors.background,
                disabledContainerColor = colors.background,
            ),
            onValueChange = { uiState.setIpv4(it) },
            label = { Text(stringResource(R.string.ipv4), style = typography.body) },
            isError = false,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                },
            ),
        )

        OutlinedTextField(
            value = data.ipv6,
            singleLine = true,
            shape = shapes.large,
            modifier = Modifier,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colors.background,
                unfocusedContainerColor = colors.background,
                disabledContainerColor = colors.background,
            ),
            onValueChange = { uiState.setIpv6(it) },
            label = { Text(stringResource(R.string.ipv6), style = typography.body) },
            isError = false,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                },
            ),
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = data.useIpv6,
                onCheckedChange = { _ -> uiState.toggleIpV6() },
            )
            Spacer(modifier = Modifier.padding(horizontal = spacing.SpacingMedium))
            Column(
                modifier = Modifier.pointerInput(null) {
                    detectTapGestures(
                        onTap = { _ -> uiState.toggleIpV6() },
                    )
                },
            ) {
                Text(
                    text = stringResource(R.string.use_ipv6),
                    style = typography.labelBold,
                )
                Text(
                    text = stringResource(R.string.wifi_prevention),
                    style = typography.body,
                )
            }
        }
    }
}
