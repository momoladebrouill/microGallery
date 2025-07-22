package studio.lunabee.amicrogallery.bottomBar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BottomBarManager {

    private val _shown: MutableStateFlow<Boolean> = MutableStateFlow(true)
    internal val shown: StateFlow<Boolean> = _shown.asStateFlow()

    fun setValue(value: Boolean) {
        _shown.value = value
    }
}
