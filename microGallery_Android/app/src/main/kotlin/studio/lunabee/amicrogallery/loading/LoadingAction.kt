package studio.lunabee.amicrogallery.loading

import studio.lunabee.microgallery.android.data.MYear

sealed interface LoadingAction {
    sealed interface ErrorAction : LoadingAction
    sealed interface FetchingAction : LoadingAction
    object FoundAll : FetchingAction
    data class FoundYear(
        val years: List<MYear>,
    ) : FetchingAction

    data class Error(val errorMessage: String?) : FetchingAction
}
