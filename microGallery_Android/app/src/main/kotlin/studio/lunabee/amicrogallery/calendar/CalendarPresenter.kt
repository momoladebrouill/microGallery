package studio.lunabee.amicrogallery.calendar

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.microgallery.android.data.MMonth
import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.domain.calendar.CalendarRepository

class CalendarPresenter(
    val calendarRepository: CalendarRepository,
) : LBSinglePresenter<CalendarUiState, CalendarNavScope, CalendarAction>() {
    override val flows: List<Flow<CalendarAction>> = emptyList()

    override fun getInitialState(): CalendarUiState = CalendarUiState(
        years = listOf(),
        monthsOfYears = mapOf(),
        photosOfMonth = mapOf(),
        expandedMonths = setOf(),
        yearSelected = null,
        jumpToSettings = { emitUserAction(CalendarAction.JumpToSettings) },
        jumpToYear = { emitUserAction(CalendarAction.JumpToYear(it)) },
        resetToHome = { emitUserAction(CalendarAction.ResetToHome) },
        showPhoto = { emitUserAction(CalendarAction.ShowPhoto(it)) },
        askForExpand = { year: MYear, month: MMonth -> emitUserAction(CalendarAction.AskForExpand(year, month)) },
    )

    override fun initReducer(): LBSingleReducer<CalendarUiState, CalendarNavScope, CalendarAction> {
        return CalendarReducer(viewModelScope, ::emitUserAction, calendarRepository)
    }

    init {
        emitUserAction(CalendarAction.PopulateYears)
    }

    override val content: @Composable ((CalendarUiState) -> Unit) = { CalendarScreen(it) }
}
