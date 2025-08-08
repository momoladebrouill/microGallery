package studio.lunabee.amicrogallery.loading

import ErrorReducer
import FetchingReducer
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import com.lunabee.lbcore.model.LBResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import studio.lunabee.amicrogallery.android.error.CoreError
import studio.lunabee.compose.presenter.LBPresenter
import studio.lunabee.compose.presenter.LBSimpleReducer
import studio.lunabee.microgallery.android.domain.SetClientHttpUrlUseCase
import studio.lunabee.microgallery.android.domain.loading.usecase.ListYearsFlowUseCase
import studio.lunabee.microgallery.android.domain.loading.usecase.PhotoDbIsEmptyUseCase
import studio.lunabee.microgallery.android.domain.loading.usecase.UpdateTreeUseCase
import studio.lunabee.microgallery.android.domain.settings.usecase.ObserveSettingsUseCase

class LoadingPresenter(
    val updateTreeUseCase: UpdateTreeUseCase,
    val photoDbIsEmptyUseCase: PhotoDbIsEmptyUseCase,
    val setClientHttpUrlUseCase: SetClientHttpUrlUseCase,
    val observeSettingsUseCase: ObserveSettingsUseCase,
    val yearsFlowUseCase: ListYearsFlowUseCase,
) : LBPresenter<LoadingUiState, LoadingNavScope, LoadingAction>() {
    val yearsFlow = yearsFlowUseCase().map { LoadingAction.FoundYear(it) }

    init {
        viewModelScope.launch {
            setClientHttpUrlUseCase(observeSettingsUseCase().first())
            val result = CoreError.runCatching {
                if (photoDbIsEmptyUseCase()) {
                    updateTreeUseCase()
                }
            }
            when (result) {
                is LBResult.Failure<*> -> emitUserAction(LoadingAction.Error(result.throwable?.message))
                is LBResult.Success<*> -> emitUserAction(LoadingAction.FoundAll)
            }
        }
    }

    override val flows: List<Flow<LoadingAction>> = listOf(
        yearsFlow,
    )

    override fun getInitialState(): LoadingUiState = LoadingUiState.Fetching(
        emptyList(),
        { emitUserAction(LoadingAction.JumpToSettings) },
    )

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
