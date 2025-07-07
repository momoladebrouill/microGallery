package studio.lunabee.amicrogallery.untimed

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.microgallery.android.domain.untimed.UntimedRepository
import studio.lunabee.microgallery.android.domain.untimed.usecase.ObserveUntimedUseCase

class UntimedPresenter(
    val untimedRepository: UntimedRepository,
    val observeUntimedUseCase: ObserveUntimedUseCase
) : LBSinglePresenter<UntimedUiState, UntimedNavScope, UntimedAction>() {

    val untimedList = observeUntimedUseCase().map { UntimedAction.GotTheList(it) }

    override val flows: List<Flow<UntimedAction>> = listOf(
        untimedList
    )

    override fun initReducer(): LBSingleReducer<UntimedUiState, UntimedNavScope, UntimedAction> {
        return UntimedReducer(
            coroutineScope = viewModelScope,
            emitUserAction = ::emitUserAction,
        )
    }



    override fun getInitialState(): UntimedUiState = UntimedUiState(
        images = listOf(),
        showPhoto = { emitUserAction(UntimedAction.ShowPhoto(it)) },
    )

    override val content: @Composable (UntimedUiState) -> Unit = { UntimedScreen(it) }
}
