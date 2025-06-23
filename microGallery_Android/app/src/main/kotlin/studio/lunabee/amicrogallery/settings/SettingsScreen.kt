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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreSpacing
import studio.lunabee.amicrogallery.app.R

fun getAppVersion(context: Context): String {
    return try {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        packageInfo.versionName ?: "Unknown"
    } catch (e: PackageManager.NameNotFoundException) {
        "Unknown"
    }
}

@Composable
fun TitleSettingsEntry(modifier:Modifier = Modifier){
    Text(
        text = stringResource(R.string.settings),
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier
    )
}

@Composable
fun IPAddressesSettingsEntry(modifier : Modifier = Modifier){
    Column(modifier = modifier) {
    Text(
        text = stringResource(R.string.adress_of_server),
        style = MaterialTheme.typography.titleMedium
    )

    OutlinedTextField(
        value = "",
        singleLine = true,
        shape = shapes.large,
        modifier = Modifier,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorScheme.surface,
            unfocusedContainerColor = colorScheme.surface,
            disabledContainerColor = colorScheme.surface,
        ),
        onValueChange = { newValue: String -> Unit },
        label = { Text(stringResource(R.string.ipv4)) },
        isError = false,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { }
        )
    )

    OutlinedTextField(
        value = "",
        singleLine = true,
        shape = shapes.large,
        modifier = Modifier,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorScheme.surface,
            unfocusedContainerColor = colorScheme.surface,
            disabledContainerColor = colorScheme.surface,
        ),
        onValueChange = { newValue: String -> Unit },
        label = { Text(stringResource(R.string.ipv6)) },
        isError = false,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { }
        )
    )
}}

@Composable
fun PreviewSettingsEntry(modifier : Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.previsualisation_options),
            style = MaterialTheme.typography.titleMedium
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween)
        {

            Column {
                Text(
                    text = stringResource(R.string.preview_in_hd),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(R.string.consumes_more),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Checkbox(
                modifier = Modifier.align(alignment = Alignment.CenterVertically),
                checked = true,
                onCheckedChange = { newValue: Boolean -> Unit }
            )
        }
    }
}

@Composable
fun CacheSettingsEntry(modifier : Modifier = Modifier){
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.cache),
            style = MaterialTheme.typography.titleMedium
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween)
        {

            Column {
                Text(
                    text = stringResource(R.string.use_cache),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(R.string.consume_less_but_more_storage),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Checkbox(
                modifier = Modifier.align(alignment = Alignment.CenterVertically),
                checked = true,
                onCheckedChange = { newValue: Boolean -> Unit }
            )
        }
        Spacer(Modifier.height(CoreSpacing.SpacingLarge))
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {}
            ) {
                Text(
                    text = stringResource(R.string.empty_cache)
                )
            }
        }
    }
}

@Composable
fun ServerStatisticsSettingsEntry(modifier : Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = stringResource(R.string.server_statistics),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(alignment = Alignment.CenterVertically)
            )
            Button(
                onClick = {}
            ) {
                Text(text = stringResource(R.string.refresh))
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = stringResource(R.string.temperature)
            )
            Text(
                text = stringResource(R.string.celcius, 42)
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = stringResource(R.string.quantity_of_pictures)
            )
            Text(
                text = stringResource(R.string.nphotos, 14572)
            )
        }
    }
}


@Composable
fun SettingsScreen(modifier : Modifier = Modifier){
    val settingsEntries: List<@Composable (modifier : Modifier) -> Unit> = listOf(
        // mod as a short term for modifier
        {mod -> TitleSettingsEntry(mod)},
        {mod -> IPAddressesSettingsEntry(mod)},
        {mod -> PreviewSettingsEntry(mod)},
        {mod -> CacheSettingsEntry(mod)},
        {mod -> ServerStatisticsSettingsEntry(mod)}
    )
    Column {
        val entryModifier = Modifier
        LazyColumn(modifier =
            modifier
                .background(colorScheme.background),
            contentPadding = PaddingValues(CoreSpacing.SpacingMedium),
            verticalArrangement = Arrangement.spacedBy(CoreSpacing.SpacingLarge)

        ) {
            items(settingsEntries) { entry ->
                Column {
                    entry(entryModifier)
                    Spacer(Modifier.height(CoreSpacing.SpacingLarge))
                    HorizontalDivider(modifier = Modifier.fillMaxWidth())
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)
        {
            Text(stringResource(R.string.application_name) + getAppVersion(LocalContext.current),
                style = MaterialTheme.typography.labelSmall)
        }
    }

}


