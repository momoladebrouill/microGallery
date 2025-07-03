package studio.lunabee.amicrogallery.loading

sealed interface LoadingAction {
    object FoundData : LoadingAction

    object FoundSettings : LoadingAction
    object Reload : LoadingAction
    data class Error(val errorMessage: String?) : LoadingAction
}
