package studio.lunabee.amicrogallery.calendar

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import com.lunabee.lbcore.model.LBResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import studio.lunabee.amicrogallery.loading.LoadingAction
import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.microgallery.android.domain.calendar.CalendarRepository
import studio.lunabee.microgallery.android.domain.calendar.usecase.LoadTreeUseCase
import studio.lunabee.microgallery.android.domain.loading.usecase.UpdateTreeUseCase

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

                is LBResult.Failure -> {
                    // fall on error
                }
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
