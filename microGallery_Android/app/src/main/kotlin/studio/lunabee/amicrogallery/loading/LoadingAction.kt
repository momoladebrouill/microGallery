package studio.lunabee.amicrogallery.loading

sealed interface LoadingAction {
    class FoundData() : LoadingAction
    class Reload() : LoadingAction
    data class Error(val errorMessage: String?) : LoadingAction
}
