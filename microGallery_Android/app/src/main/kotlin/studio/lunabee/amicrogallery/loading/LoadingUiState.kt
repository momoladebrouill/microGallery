package studio.lunabee.amicrogallery.loading

import studio.lunabee.compose.presenter.PresenterUiState

interface LoadingUiState : PresenterUiState {
    class Default : LoadingUiState
    data class Error(val errorMessage: String?) : LoadingUiState
}
