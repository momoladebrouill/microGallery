package studio.lunabee.amicrogallery.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.lunabee.lbcore.model.LBResult
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.microgallery.android.domain.Node
import studio.lunabee.microgallery.android.domain.calendar.usecase.UpdateTreeUseCase

class CalendarPresenter(
    private val updateTreeUseCase: UpdateTreeUseCase,
) : LBSinglePresenter<CalendarUiState, CalendarNavScope, CalendarAction>() {
    override val flows: List<Flow<CalendarAction>> = emptyList()
    var hazeState: HazeState? = null
    var rootNode by mutableStateOf<Node?>(null)

    override fun getInitialState(): CalendarUiState = CalendarUiState(
        rootNode = rootNode,
    )

    override fun initReducer(): LBSingleReducer<CalendarUiState, CalendarNavScope, CalendarAction> {
        return CalendarReducer(viewModelScope, ::emitUserAction)
    }

    init {
        refreshEvent()
    }

    private fun refreshEvent() {
        viewModelScope.launch {
            when (val result: LBResult<Node> = updateTreeUseCase()) {
                is LBResult.Success -> {
                    rootNode = result.data
                    emitUserAction(CalendarAction.StopRefreshing(result.data!!))
                }
                is LBResult.Failure -> { /*TODO : Act upon failure, largely fixed in other PR*/ }
            }
        }
    }

    override val content: @Composable ((CalendarUiState) -> Unit) = { CalendarScreen(it, ::emitUserAction) }
}
