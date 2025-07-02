package studio.lunabee.amicrogallery.settings

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.colors
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.spacing
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.typography
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.microgallery.android.data.SettingsData
import studio.lunabee.amicrogallery.core.ui.R as CoreUi

fun Context.getAppVersion(): String {
    return try {
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        packageInfo.versionName ?: "Unknown"
    } catch (e: PackageManager.NameNotFoundException) {
        "Unknown"
    }
}

@Composable
fun TitleSettingsEntry(modifier: Modifier = Modifier, fireAction: (SettingsAction) -> Unit) {
    Row(modifier = modifier) {
        IconButton(
            onClick = { fireAction(SettingsAction.JumpBack) },
            modifier = modifier.align(Alignment.CenterVertically),
        ) {
            Icon(
                painter = painterResource(CoreUi.drawable.back_arrow),
                contentDescription = stringResource(R.string.navigate_back),
                modifier = Modifier.size(spacing.SpacingLarge),
            )
        }
        Spacer(modifier = Modifier.padding(spacing.SpacingSmall))
        Text(
            text = stringResource(R.string.settings),
            style = typography.header,
            modifier = Modifier.align(alignment = Alignment.CenterVertically),
        )
    }
}

@Composable
fun IPAddressesSettingsEntry(modifier: Modifier = Modifier, data : SettingsData) {
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
            onValueChange = { newValue: String -> Unit },
            label = { Text(stringResource(R.string.ipv4), style = typography.body) },
            isError = false,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // TODO : register the new ip
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
            onValueChange = { newValue: String -> Unit },
            label = { Text(stringResource(R.string.ipv6), style = typography.body) },
            isError = false,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = { },
            ),
        )
    }
}

@Composable
fun PreviewSettingsEntry(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.previsualisation_options),
            style = typography.title,
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(
                    text = stringResource(R.string.preview_in_hd),
                    style = typography.bodyBold,
                )
                Text(
                    text = stringResource(R.string.consumes_more),
                    style = typography.body,
                )
            }
            Checkbox(
                modifier = Modifier.align(alignment = Alignment.CenterVertically),
                checked = true,
                onCheckedChange = { newValue: Boolean -> Unit },
            )
        }
    }
}

@Composable
fun CacheSettingsEntry(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.cache),
            style = typography.title,
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(
                    text = stringResource(R.string.use_cache),
                    style = typography.bodyBold,
                )
                Text(
                    text = stringResource(R.string.consume_less_but_more_storage),
                    style = typography.body,
                )
            }
            Checkbox(
                modifier = Modifier.align(alignment = Alignment.CenterVertically),
                checked = true,
                onCheckedChange = { newValue: Boolean -> Unit },
            )
        }
        Spacer(Modifier.height(spacing.SpacingLarge))
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {},
            ) {
                Text(
                    text = stringResource(R.string.empty_cache),
                    style = typography.body,
                )
            }
        }
    }
}

@Composable
fun ServerStatisticsSettingsEntry(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = stringResource(R.string.server_statistics),
                style = typography.title,
                modifier = Modifier.align(alignment = Alignment.CenterVertically),
            )
            Button(
                onClick = {},
            ) {
                Text(
                    text = stringResource(R.string.refresh),
                    style = typography.body,
                )
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = stringResource(R.string.temperature),
                style = typography.body,
            )
            Text(
                text = stringResource(R.string.celcius, 42),
                style = typography.body,
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = stringResource(R.string.quantity_of_pictures),
                style = typography.body,
            )
            Text(
                text = stringResource(R.string.nphotos, 14572),
                style = typography.body,
            )
        }
    }
}

@Composable
fun SettingsScreen(uiState: SettingsUiState, fireAction: (SettingsAction) -> Unit) {
    if(uiState.data == null)
        Text(stringResource( R.string.loading), style = typography.body)
    else {
        val settingsEntries: List<@Composable (modifier: Modifier) -> Unit> = listOf(
            // mod as a short term for modifier
            { mod -> TitleSettingsEntry(mod, fireAction) },
            { mod -> IPAddressesSettingsEntry(mod, uiState.data) },
            { mod -> PreviewSettingsEntry(mod) },
            { mod -> CacheSettingsEntry(mod) },
            { mod -> ServerStatisticsSettingsEntry(mod) },
        )
        Column(modifier = Modifier.statusBarsPadding()) {
            val entryModifier = Modifier
            LazyColumn(
                modifier = Modifier.background(colors.background),
                contentPadding = PaddingValues(spacing.SpacingMedium),
                verticalArrangement = Arrangement.spacedBy(spacing.SpacingLarge),

                ) {
                items(settingsEntries) { entry ->
                    Column {
                        entry(entryModifier)
                        Spacer(Modifier.height(spacing.SpacingMedium))
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    stringResource(R.string.application_name) + LocalContext.current.getAppVersion(),
                    style = typography.labelBold,
                )
            }
        }
    }
}
