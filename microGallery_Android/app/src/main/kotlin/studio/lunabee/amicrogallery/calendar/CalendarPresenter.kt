package studio.lunabee.amicrogallery.calendar

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import com.lunabee.lbcore.model.LBResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import studio.lunabee.amicrogallery.loading.LoadingAction
import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.microgallery.android.data.YearPreview
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

    fun populateYears(){
        viewModelScope.launch {
            when (val result = LoadTreeUseCase(calendarRepository).invoke()) {
                is LBResult.Success -> {
                    // not very nice, but will be fixed in feature/better-ui
                    emitUserAction(CalendarAction.GotYears(result.successData.keys.toList()))
                    emitUserAction(CalendarAction.GotMonthsOfYears(result.successData))
                }

    // then  the list of months for each year
    fun populateMonths(years: List<String>) {
        viewModelScope.launch {
            val monthsOfYears = mutableMapOf<String, List<String>>()
            for (year in years) {
                val monthsOfYear = calendarRepository.getMonthsInYear(year)
                monthsOfYears[year] = monthsOfYear
                is LBResult.Failure -> {
                    // fall on error
                }
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
