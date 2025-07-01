package studio.lunabee.amicrogallery.android.core.ui.component.topappbar

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme

@Composable
fun NavBarButton(
    onClick: () -> Unit,
    icon: Painter,
    description: String,
    activated: Boolean,
) {
    IconButton(onClick = onClick) {
        Icon(
            icon,
            contentDescription = description,
            tint = if (activated) MicroGalleryTheme.colors.onMain
            else MicroGalleryTheme.colors.disabled
        )
    }
}
