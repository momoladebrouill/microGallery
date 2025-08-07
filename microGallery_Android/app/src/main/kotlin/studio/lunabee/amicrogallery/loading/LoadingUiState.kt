package studio.lunabee.amicrogallery.loading

import studio.lunabee.compose.presenter.PresenterUiState
import studio.lunabee.microgallery.android.data.MYear

sealed interface LoadingUiState : PresenterUiState {

    data class Error(
        val errorMessage: String?,
        val reload: () -> Unit,
    ) : LoadingUiState

    data class Fetching(
        val years: List<MYear>,
    ) : LoadingUiState
}
