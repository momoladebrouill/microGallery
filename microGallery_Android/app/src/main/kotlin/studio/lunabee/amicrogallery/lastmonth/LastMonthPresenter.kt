package studio.lunabee.amicrogallery.lastmonth

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.microgallery.android.data.MicroPicture
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.domain.lastMonth.LastMonthRepository
import studio.lunabee.microgallery.android.domain.lastMonth.usecase.ObserveLastMonthUseCase

class LastMonthPresenter(
    val lastMonthRepository: LastMonthRepository,
    val observeLastMonthUseCase: ObserveLastMonthUseCase
) : LBSinglePresenter<LastMonthUiState, LastMonthNavScope, LastMonthAction>() {

    val lastMonth = observeLastMonthUseCase().map { LastMonthAction.GotTheList(it) }

    override val flows: List<Flow<LastMonthAction>> = listOf(lastMonth)

    override fun initReducer(): LBSingleReducer<LastMonthUiState, LastMonthNavScope, LastMonthAction> {
        return LastMonthReducer(
            coroutineScope = viewModelScope,
            emitUserAction = ::emitUserAction,
            lastMonthRepository,
        )
    }

    override fun getInitialState(): LastMonthUiState = LastMonthUiState(
        pictures = listOf<MicroPicture>(),
        showPhoto = { emitUserAction(LastMonthAction.ShowPhoto(it)) },
    )

    override val content: @Composable (LastMonthUiState) -> Unit = { LastMonthScreen(it) }
}
