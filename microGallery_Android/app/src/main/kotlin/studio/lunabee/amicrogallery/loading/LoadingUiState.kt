package studio.lunabee.amicrogallery.loading

import studio.lunabee.compose.presenter.PresenterUiState

sealed interface LoadingUiState : PresenterUiState {

    data class Error(
        val errorMessage: String?,
        val reload: () -> Unit,
    ) : LoadingUiState
    data class Fetching(
        val foundMap: Map<String, Boolean>
    ) : LoadingUiState
}
