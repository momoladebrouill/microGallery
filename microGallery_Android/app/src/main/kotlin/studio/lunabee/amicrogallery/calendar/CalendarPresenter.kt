package studio.lunabee.amicrogallery.calendar

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.microgallery.android.domain.calendar.CalendarRepository

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

    // first get the list of years
    fun populateYears() {
        viewModelScope.launch {
            val yearsAndMonths: List<Pair<String,String>> = calendarRepository.getYearsAndExample()
            emitUserAction(CalendarAction.GotYears(yearsAndMonths))
            populateMonths(yearsAndMonths.map {(year,_)-> year})
        }
    }

    // then  the list of months for each year
    fun populateMonths(years: List<String>) {
        viewModelScope.launch {
            val monthsOfYears = mutableMapOf<String, List<String>>()
            for (year in years) {
                val monthsOfYear = calendarRepository.getMonthsInYear(year)
                monthsOfYears[year] = monthsOfYear
            }
            emitUserAction(CalendarAction.GotMonthsOfYears(monthsOfYears))
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
