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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import studio.lunabee.amicrogallery.settings.SettingsAction
import studio.lunabee.microgallery.android.data.SettingsData

@Composable
fun IPAddressesSettingsEntry(modifier: Modifier = Modifier, data: SettingsData, fireAction: (SettingsAction) -> Unit) {
    var ipv4: String by remember { mutableStateOf(data.ipv4) }
    var ipv6: String by remember { mutableStateOf(data.ipv6) }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.adress_of_server),
            style = typography.title,
        )

        OutlinedTextField(
            value = ipv4,
            singleLine = true,
            shape = shapes.large,
            modifier = Modifier,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colors.background,
                unfocusedContainerColor = colors.background,
                disabledContainerColor = colors.background,
            ),
            onValueChange = { ipv4 = it },
            label = { Text(stringResource(R.string.ipv4), style = typography.body) },
            isError = false,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    fireAction(SettingsAction.SetParameter(data.copy(ipv4 = ipv4)))
                    keyboardController?.hide()
                },
            ),
        )

        OutlinedTextField(
            value = ipv6,
            singleLine = true,
            shape = shapes.large,
            modifier = Modifier,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colors.background,
                unfocusedContainerColor = colors.background,
                disabledContainerColor = colors.background,
            ),
            onValueChange = { ipv6 = it },
            label = { Text(stringResource(R.string.ipv6), style = typography.body) },
            isError = false,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    fireAction(SettingsAction.SetParameter(data.copy(ipv6 = ipv6)))
                    keyboardController?.hide()
                },
            ),
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = data.useIpv6,
                onCheckedChange = {
                    println("top top ${data.useIpv6}")
                    fireAction(SettingsAction.SetParameter(data.copy(useIpv6 = !data.useIpv6)))
                                  },
            )
            Spacer(modifier = Modifier.padding(horizontal = spacing.SpacingMedium))
            Column(
                modifier = Modifier.pointerInput(null) {
                    detectTapGestures(
                        onTap = {
                            println("tap tap ${data.useIpv6}")
                            fireAction(SettingsAction.SetParameter(data.copy(useIpv6 = !data.useIpv6)))
                                },
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
