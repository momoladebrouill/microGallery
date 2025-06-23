package studio.lunabee.amicrogallery.android.core.ui.component.topappbar

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

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
            tint = if (activated) { Color.Unspecified } else {
                MaterialTheme.colorScheme.surfaceDim
            },
        )
    }
}
