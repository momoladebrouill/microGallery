package studio.lunabee.amicrogallery.untimed

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.domain.untimed.UntimedRepository

class UntimedPresenter(
    val untimedRepository: UntimedRepository,
) : LBSinglePresenter<UntimedUiState, UntimedNavScope, UntimedAction>() {

    override val flows: List<Flow<UntimedAction>> = listOf()

    override fun initReducer(): LBSingleReducer<UntimedUiState, UntimedNavScope, UntimedAction> {
        return UntimedReducer(
            coroutineScope = viewModelScope,
            emitUserAction = ::emitUserAction,
        )
    }

    init {
        grabPicturesList()
    }

    fun grabPicturesList() {
        viewModelScope.launch {
            val photos: List<Picture> = untimedRepository.getPicturesUntimed()
            emitUserAction(UntimedAction.GotTheList(photos))
        }
    }

    override fun getInitialState(): UntimedUiState = UntimedUiState(
        images = listOf(),
        showPhoto = { emitUserAction(UntimedAction.ShowPhoto(it)) },
    )

    override val content: @Composable (UntimedUiState) -> Unit = { UntimedScreen(it) }
}
