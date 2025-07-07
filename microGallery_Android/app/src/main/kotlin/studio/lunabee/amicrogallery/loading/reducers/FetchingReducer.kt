import com.lunabee.lbcore.model.LBResult
import kotlinx.coroutines.CoroutineScope
import studio.lunabee.amicrogallery.loading.LoadingAction
import studio.lunabee.amicrogallery.loading.LoadingAction.*
import studio.lunabee.amicrogallery.loading.LoadingNavScope
import studio.lunabee.amicrogallery.loading.LoadingUiState
import studio.lunabee.compose.presenter.LBReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult
import studio.lunabee.compose.presenter.withSideEffect
import studio.lunabee.microgallery.android.domain.loading.LoadingRepository
import studio.lunabee.microgallery.android.domain.loading.usecase.UpdateTreeUseCase
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository


class FetchingReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (LoadingAction) -> Unit,
) : LBReducer<LoadingUiState.Fetching, LoadingUiState, LoadingNavScope, LoadingAction, FetchingAction>() {
    override suspend fun reduce(actualState: LoadingUiState.Fetching,
        action: FetchingAction,
        performNavigation: (LoadingNavScope.() -> Unit) -> Unit): ReduceResult<LoadingUiState> {
        return when(action){
            FoundAll -> actualState withSideEffect {
                performNavigation {navigateToDashboard()}
            }

            is Error -> LoadingUiState.Error(
                errorMessage = action.errorMessage,
                reload = { }
            ).asResult()


        }
    }

    override fun filterAction(action: LoadingAction): Boolean {
        return action is FetchingAction
    }

    override fun filterUiState(actualState: LoadingUiState): Boolean {
        return actualState is LoadingUiState.Fetching
    }

}
