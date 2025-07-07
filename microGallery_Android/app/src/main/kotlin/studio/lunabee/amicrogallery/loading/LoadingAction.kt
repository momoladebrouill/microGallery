package studio.lunabee.amicrogallery.loading

sealed interface LoadingAction {
    sealed interface ErrorAction : LoadingAction
    sealed interface FetchingAction : LoadingAction
    object FoundAll : FetchingAction
    data class Error(val errorMessage: String?) : FetchingAction
}
