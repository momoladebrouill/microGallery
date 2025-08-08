package studio.lunabee.amicrogallery.snackbar

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

class SnackBarViewModel(
    private val snackbarManager: SnackBarManager,
) : ViewModel() {

    val shownSnackBar: StateFlow<CoreSnackBarData?> = snackbarManager.shownSnackBar

    fun consumeSnackBar(): Unit = snackbarManager.consumeSnackBar()
}
