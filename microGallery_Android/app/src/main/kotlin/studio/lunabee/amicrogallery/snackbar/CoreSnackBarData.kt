package studio.lunabee.amicrogallery.snackbar

import studio.lunabee.compose.core.LbcTextSpec

data class CoreSnackBarData(
    val message: LbcTextSpec,
    val type: SnackbarType,
    val action: SnackbarAction? = null,
)

enum class SnackbarType {
    Error,
    Default,
}
