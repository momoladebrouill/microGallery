package studio.lunabee.amicrogallery.loading

import studio.lunabee.compose.presenter.PresenterUiState

sealed interface LoadingUiState : PresenterUiState {
    data class Error(
        val errorMessage: String?,
        val reload: () -> Unit,
    ) : LoadingUiState
    data class Default(
        val log : Int? = null //string res to show status
    ) : LoadingUiState
}
