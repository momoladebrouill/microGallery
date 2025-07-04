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
import studio.lunabee.microgallery.android.domain.settings.usecase.GetSettingsUseCase

class FetchingReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (LoadingAction) -> Unit,
    val settingsRepository: SettingsRepository,
    val loadingRepository: LoadingRepository
) : LBReducer<LoadingUiState.Fetching, LoadingUiState, LoadingNavScope, LoadingAction, FetchingAction>() {
    override suspend fun reduce(actualState: LoadingUiState.Fetching,
        action: FetchingAction,
        performNavigation: (LoadingNavScope.() -> Unit) -> Unit): ReduceResult<LoadingUiState> {
        return when(action){
            FoundAll -> actualState withSideEffect {
                performNavigation {navigateToDashboard()}
            }

            FoundData -> actualState.copy(
                foundMap = actualState.foundMap + ("data" to true)
            ) withSideEffect {
                emitUserAction(CheckIfAll)
            }

            is FoundSettings -> actualState.copy(
                foundMap = actualState.foundMap + ("settings" to true)
            ) withSideEffect {
                emitUserAction(CheckIfAll)
            }

            LoadAll -> actualState withSideEffect {
                emitUserAction(LoadData)
                emitUserAction(LoadSettings)

            }

            LoadData -> actualState withSideEffect {
                println("loading data...")
                when (val result: LBResult<Unit> = UpdateTreeUseCase(loadingRepository).invoke()) {
                    is LBResult.Success -> {
                        emitUserAction(FoundData)
                    }
                    is LBResult.Failure<Unit> -> {
                        emitUserAction(Error(result.throwable?.message))
                    }
                }
            }

            LoadSettings -> actualState withSideEffect {
                when (val result: LBResult<Unit> = GetSettingsUseCase(settingsRepository).invoke()) {
                    is LBResult.Success -> {
                        emitUserAction(FoundSettings)
                    }
                    is LBResult.Failure<Unit> -> {
                        emitUserAction(Error(result.throwable?.message))
                    }
                }
            }
            CheckIfAll -> actualState withSideEffect {
                if(actualState.foundMap.values.all {it})
                    emitUserAction(FoundAll)
            }

            is Error -> LoadingUiState.Error(
                errorMessage = action.errorMessage,
                reload = { emitUserAction(LoadingAction.LoadAll) }
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
