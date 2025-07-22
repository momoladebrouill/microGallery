package studio.lunabee.amicrogallery.loading

import ErrorReducer
import FetchingReducer
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import studio.lunabee.amicrogallery.bottomBar.BottomBarViewModel
import studio.lunabee.compose.presenter.LBPresenter
import studio.lunabee.compose.presenter.LBSimpleReducer
import studio.lunabee.microgallery.android.domain.loading.usecase.ListYearsFlowUseCase
import studio.lunabee.microgallery.android.domain.loading.usecase.PhotoDbIsEmptyUseCase
import studio.lunabee.microgallery.android.domain.loading.usecase.UpdateTreeUseCase

class LoadingPresenter(
    val updateTreeUseCase: UpdateTreeUseCase,
    val photoDbIsEmptyUseCase: PhotoDbIsEmptyUseCase,
    val yearsFlowUseCase: ListYearsFlowUseCase,

) : LBPresenter<LoadingUiState, LoadingNavScope, LoadingAction>() {
    val yearsFlow = yearsFlowUseCase().map { LoadingAction.FoundYear(it) }

    init {

        viewModelScope.launch {
            if (photoDbIsEmptyUseCase()) {
                updateTreeUseCase()
            }
            emitUserAction(LoadingAction.FoundAll)
        }
    }

    override val flows: List<Flow<LoadingAction>> = listOf(
        yearsFlow,
    )

    override fun getInitialState(): LoadingUiState = LoadingUiState.Fetching(emptyList())

    override fun getReducerByState(actualState: LoadingUiState): LBSimpleReducer<LoadingUiState, LoadingNavScope, LoadingAction> {
        return when (actualState) {
            is LoadingUiState.Fetching -> FetchingReducer(
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
