package studio.lunabee.amicrogallery.loading

import studio.lunabee.compose.presenter.PresenterUiState

interface LoadingUiState : PresenterUiState {
    data class Default(
        val log : Int? = null //string res to show status
    ) : LoadingUiState
    data class Error(val errorMessage: String?) : LoadingUiState
}
