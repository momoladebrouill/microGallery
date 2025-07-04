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
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository

class LoadingPresenter(
    val loadingRepository: LoadingRepository,
    val settingsRepository: SettingsRepository,
    val isSettingsUpUseCase: Flow<Boolean>,
) : LBPresenter<LoadingUiState, LoadingNavScope, LoadingAction>() {
    val settingUp = isSettingsUpUseCase().map {
        LoadingAction.FoundSetting(it)
    }

    override val flows: List<Flow<LoadingAction>> = listOf(
        settingUp,

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
                settingsRepository = settingsRepository,
                loadingRepository = loadingRepository
            )
            is LoadingUiState.Error -> ErrorReducer(
                coroutineScope = viewModelScope,
                emitUserAction = ::emitUserAction
            )
        }
    }

    init {
        emitUserAction(LoadingAction.LoadAll)
    }

    override val content: @Composable ((LoadingUiState) -> Unit) = { LoadingScreen(it) }
}
