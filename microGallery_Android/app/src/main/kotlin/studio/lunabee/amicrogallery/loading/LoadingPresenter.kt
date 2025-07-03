package studio.lunabee.amicrogallery.loading

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.microgallery.android.domain.loading.LoadingRepository
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository

class LoadingPresenter(
    val loadingRepository: LoadingRepository,
    val settingsRepository: SettingsRepository
) : LBSinglePresenter<LoadingUiState, LoadingNavScope, LoadingAction>() {
    override val flows: List<Flow<LoadingAction>> = emptyList()

    override fun getInitialState(): LoadingUiState = LoadingUiState.Default()

    override fun initReducer(): LBSingleReducer<LoadingUiState, LoadingNavScope, LoadingAction> {
        return LoadingReducer(viewModelScope, ::emitUserAction, loadingRepository, settingsRepository)
    }

    init {
        emitUserAction(LoadingAction.Reload)
    }

    override val content: @Composable ((LoadingUiState) -> Unit) = { LoadingScreen(it) }
}
