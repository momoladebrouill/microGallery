package studio.lunabee.amicrogallery.loading

import com.lunabee.lbcore.model.LBResult
import kotlinx.coroutines.CoroutineScope
import studio.lunabee.amicrogallery.loading.LoadingUiState.Default
import studio.lunabee.amicrogallery.loading.LoadingUiState.Error
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult
import studio.lunabee.compose.presenter.withSideEffect
import studio.lunabee.microgallery.android.domain.loading.LoadingRepository
import studio.lunabee.microgallery.android.domain.loading.usecase.UpdateTreeUseCase
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.microgallery.android.data.RemoteStatus
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository
import studio.lunabee.microgallery.android.domain.settings.usecase.LoadSettingsUseCase

class LoadingReducer(

    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (LoadingAction) -> Unit,
    val loadingRepository: LoadingRepository,
    val settingsRepository: SettingsRepository
) : LBSingleReducer<LoadingUiState, LoadingNavScope, LoadingAction>() {

    override suspend fun reduce(
        actualState: LoadingUiState,
        action: LoadingAction,
        performNavigation: (LoadingNavScope.() -> Unit) -> Unit,
    ): ReduceResult<LoadingUiState> {
        return when (action) {
            is LoadingAction.FoundData -> {
                actualState withSideEffect {
                    performNavigation { navigateToDashboard() }
                }
            }

            is LoadingAction.Error ->
                when (actualState) {
                    is Default ->
                        Error(
                            action.errorMessage,
                            { emitUserAction(LoadingAction.Reload) },
                        ).asResult()

                    is Error -> actualState.copy(errorMessage = action.errorMessage).asResult()
                }

            is LoadingAction.Reload -> actualState withSideEffect {
                when (val result: LBResult<Unit> = LoadSettingsUseCase(settingsRepository).invoke()) {
                    is LBResult.Success -> {
                        emitUserAction(LoadingAction.FoundSettings)
                    }

                    is LBResult.Failure<Unit> -> {
                        emitUserAction(LoadingAction.Error(result.throwable?.message))
                    }
                }
                when (val result: LBResult<Unit> = UpdateTreeUseCase(loadingRepository).invoke()) {
                    is LBResult.Success -> {
                        emitUserAction(LoadingAction.FoundData)
                    }

                    is LBResult.Failure<Unit> -> {
                        emitUserAction(LoadingAction.Error(result.throwable?.message))
                    }
                }
            }
            LoadingAction.FoundSettings -> Default(log = R.string.found_settings).asResult()
        }
    }
}
