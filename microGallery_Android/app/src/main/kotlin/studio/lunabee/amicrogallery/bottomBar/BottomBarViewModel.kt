package studio.lunabee.amicrogallery.bottomBar

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

class BottomBarViewModel(
    val bottomBarManager: BottomBarManager,
) : ViewModel() {

    val isShown: StateFlow<Boolean> = bottomBarManager.shown

    fun showBottomBar(value: Boolean) {
        bottomBarManager.setValue(value)
    }
}
