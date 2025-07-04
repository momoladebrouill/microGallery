package studio.lunabee.amicrogallery.calendar

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import com.lunabee.lbcore.model.LBResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.microgallery.android.domain.calendar.CalendarRepository
import studio.lunabee.microgallery.android.domain.calendar.usecase.LoadTreeUseCase

class CalendarPresenter(
    val calendarRepository: CalendarRepository,
) : LBSinglePresenter<CalendarUiState, CalendarNavScope, CalendarAction>() {
    override val flows: List<Flow<CalendarAction>> = emptyList()

    override fun getInitialState(): CalendarUiState = CalendarUiState(
        years = listOf(),
        monthsOfYears = mapOf(),
        photosOfMonth = mapOf(),
        expandedMonths = setOf<Pair<String, String>>(),
    )

    override fun initReducer(): LBSingleReducer<CalendarUiState, CalendarNavScope, CalendarAction> {
        return CalendarReducer(viewModelScope, ::emitUserAction)
    }

    init {
        populateYears()
    }

    fun populateYears() {
        viewModelScope.launch {
            when (val result = LoadTreeUseCase(calendarRepository).invoke()) {
                is LBResult.Success -> {
                    emitUserAction(CalendarAction.GotYears(result.successData.first))
                    emitUserAction(CalendarAction.GotMonthsOfYears(result.successData.second))
                }

                is LBResult.Failure<*> -> TODO()
            }
        }
    }

    fun fireAction(calendarAction: CalendarAction) {
        when (calendarAction) {
            is CalendarAction.AskForExpand -> {
                emitUserAction(calendarAction)
                viewModelScope.launch {
                    val picturesInMonth = calendarRepository.getPicturesInMonth(calendarAction.year, calendarAction.month)
                    emitUserAction(CalendarAction.GotMY(calendarAction.month, calendarAction.year, picturesInMonth))
                }
            }
            else -> emitUserAction(calendarAction)
        }
    }

    override val content: @Composable ((CalendarUiState) -> Unit) = { CalendarScreen(it, fireAction = ::fireAction) }
}
