package studio.lunabee.amicrogallery.loading

sealed interface LoadingAction {

    sealed interface ErrorAction : LoadingAction
    sealed interface FetchingAction : LoadingAction
    object FoundData : FetchingAction
    data class FoundSettings(val isUp: Boolean) : FetchingAction
    object LoadSettings : FetchingAction
    object LoadData : LoadingAction, ErrorAction
    object LoadAll : FetchingAction

    object FoundAll : FetchingAction

    object CheckIfAll : FetchingAction
    data class Error(val errorMessage: String?) : FetchingAction
}
