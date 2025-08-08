package studio.lunabee.amicrogallery.settings.entries

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.colors
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.typography
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.amicrogallery.core.ui.R as CoreUi

@Composable
fun TitleSettingsEntry(modifier: Modifier = Modifier, clickAction: () -> Unit) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(R.string.settings),
            style = typography.header,
            modifier = Modifier.align(alignment = Alignment.CenterVertically),
            color = colors.onBackground,
        )
        IconButton(
            onClick = clickAction,
            modifier = modifier.align(Alignment.CenterVertically),
        ) {
            Icon(
                painter = painterResource(CoreUi.drawable.close),
                contentDescription = stringResource(R.string.navigate_back),
                tint = colors.onBackground,
            )
        }
    }
}
