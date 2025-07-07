package studio.lunabee.amicrogallery.loading

import ErrorReducer
import FetchingReducer
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import studio.lunabee.compose.presenter.LBPresenter
import studio.lunabee.compose.presenter.LBSimpleReducer
import studio.lunabee.microgallery.android.domain.loading.LoadingRepository
import studio.lunabee.microgallery.android.domain.loading.usecase.UpdateTreeUseCase
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository

class LoadingPresenter(
    val loadingRepository: LoadingRepository,
    val updateTreeUseCase: UpdateTreeUseCase,

) : LBPresenter<LoadingUiState, LoadingNavScope, LoadingAction>() {

    val updateTree = updateTreeUseCase().map { _ -> LoadingAction.FoundAll }

    override val flows: List<Flow<LoadingAction>> = listOf(
        updateTree
    )

    override fun getInitialState(): LoadingUiState = LoadingUiState.Fetching(
        foundMap = mapOf(
            "settings" to false,
            "data" to false
        )
    )

    override fun getReducerByState(actualState: LoadingUiState): LBSimpleReducer<LoadingUiState, LoadingNavScope, LoadingAction> {
        return when(actualState){
            is LoadingUiState.Fetching -> FetchingReducer(
                coroutineScope = viewModelScope,
                emitUserAction = ::emitUserAction,
            )
            is LoadingUiState.Error -> ErrorReducer(
                coroutineScope = viewModelScope,
                emitUserAction = ::emitUserAction
            )
        }
    }

    override val content: @Composable ((LoadingUiState) -> Unit) = { LoadingScreen(it) }
}
