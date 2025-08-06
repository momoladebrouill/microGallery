package studio.lunabee.amicrogallery.android.core.ui.component.photo

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import studio.lunabee.amicrogallery.android.core.ui.component.image.MicroGalleryImage
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.colors
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.radius
import studio.lunabee.amicrogallery.core.ui.R
import studio.lunabee.microgallery.android.data.MicroPicture

@Composable
@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalSharedTransitionApi::class)
fun MicroGalleryButtonImage(
    picture: MicroPicture,
    hazeState: HazeState,
    showMe: (Long) -> Unit,
    modifier: Modifier = Modifier,

) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(radius.RadiusMedium))
            .background(
                Brush.verticalGradient(
                    Pair(0.0f, colors.main),
                    Pair(1.0f, colors.second),
                ),
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { showMe(picture.id) },
                )
            },
    ) {
        MicroGalleryImage(
            picture = picture,
            defaultToHighRes = false,
            modifier = Modifier
                .align(Alignment.Center)
                .hazeSource(state = hazeState)
                .wrapContentHeight(),
            errorPainter = painterResource(R.drawable.nopicture),

        )
    }
}
