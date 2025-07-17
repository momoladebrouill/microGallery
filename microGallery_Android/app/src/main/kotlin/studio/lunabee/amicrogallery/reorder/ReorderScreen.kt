package studio.lunabee.amicrogallery.reorder

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import studio.lunabee.amicrogallery.reorder.screens.ReorderGamingScreen
import studio.lunabee.amicrogallery.reorder.screens.ReorderMenuScreen

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ReorderScreen(uiState: ReorderUiState){
    when(uiState){
        is ReorderUiState.ReorderMenuUiState -> ReorderMenuScreen(uiState)
        is ReorderUiState.ReorderGamingUiState -> ReorderGamingScreen(uiState)
    }
}