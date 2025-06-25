package studio.lunabee.amicrogallery.loading

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import com.lunabee.lbcore.model.LBResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.microgallery.android.domain.Node
import studio.lunabee.microgallery.android.domain.calendar.usecase.UpdateTreeUseCase

class LoadingPresenter(
    private val updateTreeUseCase: UpdateTreeUseCase,
) : LBSinglePresenter<LoadingUiState, LoadingNavScope, LoadingAction>() {
    override val flows: List<Flow<LoadingAction>> = emptyList()


    override fun getInitialState(): LoadingUiState = LoadingUiState.Default()

    override fun initReducer(): LBSingleReducer<LoadingUiState, LoadingNavScope, LoadingAction> {
        return LoadingReducer(viewModelScope, ::emitUserAction)
    }

    init {
        refreshEvent()
    }

    private fun refreshEvent() {
        viewModelScope.launch {
            when (val result : LBResult<Node> = updateTreeUseCase()) {
                is LBResult.Success -> {
                    emitUserAction(LoadingAction.FoundData())
                }
                is LBResult.Failure<Node> -> {
                    emitUserAction(LoadingAction.Error(result.throwable?.message))
                }
            }
        }
    }

    private fun onAction(action: LoadingAction){
        if(action is LoadingAction.Reload){
            refreshEvent()
        }
        viewModelScope.launch { emitUserAction(action) }

    }
    override val content: @Composable ((LoadingUiState) -> Unit) = { LoadingScreen(it, ::onAction) }
}
