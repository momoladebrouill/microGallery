package studio.lunabee.amicrogallery.calendar

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.flow.Flow
import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.microgallery.android.data.Node
import studio.lunabee.microgallery.android.data.Picture

class CalendarPresenter(
) : LBSinglePresenter<CalendarUiState, CalendarNavScope, CalendarAction>() {
    override val flows: List<Flow<CalendarAction>> = emptyList()
    var hazeState: HazeState? = null
    var rootNode: Node? = null

    override fun getInitialState(): CalendarUiState = CalendarUiState(
        rootNode = rootNode!!, //causes null pointer exception
    )

    override fun initReducer(): LBSingleReducer<CalendarUiState, CalendarNavScope, CalendarAction> {
        return CalendarReducer(viewModelScope, ::emitUserAction)
    }

    override val content: @Composable ((CalendarUiState) -> Unit) = { CalendarScreen(it, hazeState) }
}
