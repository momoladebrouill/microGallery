package studio.lunabee.amicrogallery.loading

import studio.lunabee.compose.presenter.PresenterUiState

sealed interface LoadingUiState : PresenterUiState {

    data class Error(
        val errorMessage: String?,
        val reload: () -> Unit,
    ) : LoadingUiState

    object Fetching : LoadingUiState
}
