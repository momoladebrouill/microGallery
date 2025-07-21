package studio.lunabee.amicrogallery.bottomBar

import androidx.lifecycle.ViewModel

class BottomBarViewModel(
    val bottomBarManager: BottomBarManager,
) : ViewModel() {

    // val shownBottomBar: StateFlow<Boolean?> = bottomBarManager.shown

    fun set(value: Boolean) {
        bottomBarManager.setValue(value)
    }
}
