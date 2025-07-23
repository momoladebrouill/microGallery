package studio.lunabee.amicrogallery.reorder

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import org.koin.compose.koinInject
import studio.lunabee.amicrogallery.bottomBar.BottomBarViewModel
import studio.lunabee.amicrogallery.reorder.screens.ReorderGamingScreen
import studio.lunabee.amicrogallery.reorder.screens.ReorderMenuScreen

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ReorderScreen(uiState: ReorderUiState) {
    val bottomBarViewModel = koinInject<BottomBarViewModel>()
    LaunchedEffect(Unit) { bottomBarViewModel.showBottomBar(uiState is ReorderUiState.ReorderMenuUiState) }
    when (uiState) {
        is ReorderUiState.ReorderMenuUiState -> ReorderMenuScreen(uiState)
        is ReorderUiState.ReorderGamingUiState -> ReorderGamingScreen(uiState)
    }
}
