package studio.lunabee.amicrogallery.snackbar

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SnackBarManager {
    private val _shownSnackBar: MutableStateFlow<CoreSnackBarData?> = MutableStateFlow(null)
    internal val shownSnackBar: StateFlow<CoreSnackBarData?> = _shownSnackBar.asStateFlow()

    fun showSnackBar(data: CoreSnackBarData) {
        _shownSnackBar.value = data
    }

    internal fun consumeSnackBar() {
        _shownSnackBar.value = null
    }
}
