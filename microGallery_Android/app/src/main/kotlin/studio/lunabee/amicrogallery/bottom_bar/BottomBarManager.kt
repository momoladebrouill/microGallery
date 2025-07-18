package studio.lunabee.amicrogallery.bottom_bar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BottomBarManager {

    var shown: Boolean = true

    fun consumeBottomBar(){
        shown = true
    }

    fun setValue(value : Boolean) = {
        println("set is called with value $value")
        shown = value
    }
}