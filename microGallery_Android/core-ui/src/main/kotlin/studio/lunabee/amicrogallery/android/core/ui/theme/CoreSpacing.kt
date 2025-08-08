package studio.lunabee.amicrogallery.android.core.ui.theme

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object CoreSpacing {
    val SpacingSmall = 4.dp
    val SpacingMedium = 16.dp
    val SpacingLarge = 32.dp
}

@Composable
fun Dp.VerticalSpacer() {
    Spacer(modifier = Modifier.height(this))
}

@Composable
fun Dp.HorizontalSpacer() {
    Spacer(modifier = Modifier.width(this))
}
