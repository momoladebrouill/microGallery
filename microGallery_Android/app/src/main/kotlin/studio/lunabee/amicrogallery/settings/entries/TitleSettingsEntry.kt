package studio.lunabee.amicrogallery.settings.entries

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.spacing
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.typography
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.amicrogallery.core.ui.R as CoreUi

@Composable
fun TitleSettingsEntry(modifier: Modifier = Modifier, clickAction: () -> Unit) {
    Row(modifier = modifier) {
        IconButton(
            onClick = clickAction,
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
