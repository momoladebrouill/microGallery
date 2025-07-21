package studio.lunabee.amicrogallery.bottom_bar

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

class BottomBarViewModel(
    val bottomBarManager: BottomBarManager
) : ViewModel() {

    //val shownBottomBar: StateFlow<Boolean?> = bottomBarManager.shown


    fun set(value : Boolean) {
        bottomBarManager.setValue(value)
    }

}