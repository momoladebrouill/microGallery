package studio.lunabee.amicrogallery.lastmonth

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.domain.lastMonth.LastMonthRepository

class LastMonthPresenter(
    val lastMonthRepository: LastMonthRepository,
) : LBSinglePresenter<LastMonthUiState, LastMonthNavScope, LastMonthAction>() {

    override val flows: List<Flow<LastMonthAction>> = listOf()

    override fun initReducer(): LBSingleReducer<LastMonthUiState, LastMonthNavScope, LastMonthAction> {
        return LastMonthReducer(
            coroutineScope = viewModelScope,
            emitUserAction = ::emitUserAction,
            lastMonthRepository,
        )
    }

    init {
        emitUserAction(LastMonthAction.GetTheList)
    }

    override fun getInitialState(): LastMonthUiState = LastMonthUiState(
        pictures = listOf<Picture>(),
        showPhoto = { emitUserAction(LastMonthAction.ShowPhoto(it)) },
    )

    override val content: @Composable (LastMonthUiState) -> Unit = { LastMonthScreen(it) }
}
