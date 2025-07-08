package studio.lunabee.amicrogallery.loading

import ErrorReducer
import FetchingReducer
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import studio.lunabee.compose.presenter.LBPresenter
import studio.lunabee.compose.presenter.LBSimpleReducer
import studio.lunabee.microgallery.android.domain.loading.usecase.UpdateTreeUseCase

class LoadingPresenter(
    val updateTreeUseCase: UpdateTreeUseCase,

) : LBPresenter<LoadingUiState, LoadingNavScope, LoadingAction>() {

    init {
        viewModelScope.launch {
            updateTreeUseCase()

            emitUserAction(LoadingAction.FoundAll)
        }
    }

    override val flows: List<Flow<LoadingAction>> = listOf()

    override fun getInitialState(): LoadingUiState = LoadingUiState.Fetching

    override fun getReducerByState(actualState: LoadingUiState): LBSimpleReducer<LoadingUiState, LoadingNavScope, LoadingAction> {
        return when (actualState) {
            LoadingUiState.Fetching -> FetchingReducer(
                coroutineScope = viewModelScope,
                emitUserAction = ::emitUserAction,
            )

            is LoadingUiState.Error -> ErrorReducer(
                coroutineScope = viewModelScope,
                emitUserAction = ::emitUserAction,
            )
        }
    }

    override val content: @Composable ((LoadingUiState) -> Unit) = { LoadingScreen(it) }
}
