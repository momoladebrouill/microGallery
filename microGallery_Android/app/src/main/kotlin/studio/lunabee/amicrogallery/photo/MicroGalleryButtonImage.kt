package studio.lunabee.amicrogallery.photo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonShapes
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import studio.lunabee.amicrogallery.android.core.ui.component.image.MicroGalleryImage
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreRadius
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreSpacing
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.microgallery.android.data.Picture

@Composable
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
fun MicroGalleryButtonImage(picture: Picture, hazeState : HazeState, showMe : (Long)->Unit){
    Button(
        onClick = { if(picture.id != null) showMe(picture.id!!)},
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.padding(PaddingValues(CoreSpacing.SpacingMedium)),
        shapes = ButtonShapes(RoundedCornerShape(CoreRadius.RadiusMedium), RoundedCornerShape(CoreRadius.RadiusMedium)),

        ) {
        Box {
            Text(text = stringResource(R.string.loading, picture.name), modifier = Modifier.align(Alignment.Center))
            MicroGalleryImage(
                url = "http://92.150.239.130" + picture.lowResPath,
                // TODO : better MicroGalleryImage to call with only a Picture (fallback to highRes etc)
                modifier = Modifier
                    .hazeSource(state = hazeState)
                    .wrapContentHeight(),
            )
        }
    }
}