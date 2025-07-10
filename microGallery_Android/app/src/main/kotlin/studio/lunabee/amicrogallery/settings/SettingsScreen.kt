package studio.lunabee.amicrogallery.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.colors
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.spacing
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.typography
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.amicrogallery.settings.entries.CacheSettingsEntry
import studio.lunabee.amicrogallery.settings.entries.IPAddressesSettingsEntry
import studio.lunabee.amicrogallery.settings.entries.ServerStatisticsSettingsEntry
import studio.lunabee.amicrogallery.settings.entries.TitleSettingsEntry
import studio.lunabee.amicrogallery.settings.entries.ViewUntimedSettingsEntry
import studio.lunabee.amicrogallery.settings.entries.VisualiseSettingsEntry
import studio.lunabee.amicrogallery.utils.getAppVersion

@Composable
fun SettingsScreen(uiState: SettingsUiState) {
    val settingsEntries: List<@Composable (modifier: Modifier) -> Unit> = listOf(
        // mod as a short term for modifier
        { mod -> TitleSettingsEntry(mod, uiState.jumpBack) },
        { mod -> IPAddressesSettingsEntry(mod, uiState) },
        { mod -> VisualiseSettingsEntry(mod, uiState) },
        { mod -> CacheSettingsEntry(mod, uiState) },
        { mod -> ServerStatisticsSettingsEntry(mod, uiState) },
        { mod -> ViewUntimedSettingsEntry(mod, uiState) },
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
