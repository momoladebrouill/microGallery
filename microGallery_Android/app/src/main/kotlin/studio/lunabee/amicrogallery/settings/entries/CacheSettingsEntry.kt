package studio.lunabee.amicrogallery.settings.entries

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.typography
import studio.lunabee.amicrogallery.app.R

@Composable
fun CacheSettingsEntry(modifier: Modifier = Modifier, clearCache: (Context) -> Unit) {
    val context = LocalContext.current
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.cache),
            style = typography.title,
        )

        Button(
            onClick = {clearCache(context)},
        ) {
            Text(
                text = stringResource(R.string.empty_cache),
                style = typography.body,
            )
        }
    }
}
