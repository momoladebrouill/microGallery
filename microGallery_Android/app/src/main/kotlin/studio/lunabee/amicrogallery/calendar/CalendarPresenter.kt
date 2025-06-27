package studio.lunabee.amicrogallery.calendar

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.domain.calendar.CalendarRepository

class CalendarPresenter(
    val calendarRepository: CalendarRepository
) : LBSinglePresenter<CalendarUiState, CalendarNavScope, CalendarAction>() {
    override val flows: List<Flow<CalendarAction>> = emptyList()
    var hazeState: HazeState? = null


    override fun getInitialState(): CalendarUiState = CalendarUiState(
        years = listOf(),
        monthsOfYears = mapOf(),
        photosOfMonth = mapOf(),
        expandedMonths = setOf<Pair<String,String>>()
    )

    init {
        populateYears()
    }

    fun populateYears(){
        viewModelScope.launch {
            val years : List<String> = calendarRepository.getYears()
            emitUserAction(CalendarAction.GotYears(years))
            populateMonths(years)
        }
    }

    fun populateMonths(years : List<String>){
        viewModelScope.launch {
            val monthsOfYears = mutableMapOf<String, List<String> >()
            for(year in years){
                val monthsOfYear = calendarRepository.getMonthsInYear(year)
                monthsOfYears[year] = monthsOfYear
            }
            emitUserAction(CalendarAction.GotMonthsOfYears(monthsOfYears))
        }
    }

    fun askForMY(month: String, year : String){
        viewModelScope.launch {
            val pictures : List<Picture> = calendarRepository.getPicturesInMonth(year, month)
            emitUserAction(CalendarAction.GotMY(month, year, pictures))
        }

    }

    fun fireAction(calendarAction: CalendarAction){
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



    override fun initReducer(): LBSingleReducer<CalendarUiState, CalendarNavScope, CalendarAction> {
        return CalendarReducer(viewModelScope, ::emitUserAction)
    }

    override val content: @Composable ((CalendarUiState) -> Unit) = { CalendarScreen(it, hazeState!!, fireAction = ::fireAction) }
}
