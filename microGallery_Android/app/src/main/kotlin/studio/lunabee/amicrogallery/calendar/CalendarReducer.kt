package studio.lunabee.amicrogallery.calendar

import com.lunabee.lbcore.model.LBResult
import kotlinx.coroutines.CoroutineScope
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult
import studio.lunabee.compose.presenter.withSideEffect
import studio.lunabee.microgallery.android.domain.calendar.CalendarRepository
import studio.lunabee.microgallery.android.domain.calendar.usecase.LoadTreeUseCase

class CalendarReducer(

    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (CalendarAction) -> Unit,
    val calendarRepository: CalendarRepository,
) : LBSingleReducer<CalendarUiState, CalendarNavScope, CalendarAction>() {

    override suspend fun reduce(
        actualState: CalendarUiState,
        action: CalendarAction,
        performNavigation: (CalendarNavScope.() -> Unit) -> Unit,
    ): ReduceResult<CalendarUiState> {
        return when (action) {
            is CalendarAction.JumpToSettings -> actualState withSideEffect { performNavigation { navigateToSettings() } }
            is CalendarAction.ShowPhoto -> actualState withSideEffect { performNavigation { navigateToPhotoViewer(action.pictureId) } }

            is CalendarAction.GotYears -> actualState.copy(years = action.years).asResult()
            is CalendarAction.GotMonthsOfYears -> actualState.copy(monthsOfYears = action.monthsOfYears).asResult()

            is CalendarAction.GotPicturesInMonth -> actualState.copy(
                photosOfMonth = actualState.photosOfMonth + (Pair(action.year, action.month) to action.pictures),
            ).asResult()

            is CalendarAction.AskForExpand -> actualState.copy(
                expandedMonths = actualState.expandedMonths + Pair(action.year, action.month),
            ) withSideEffect {
                val picturesInMonth = calendarRepository.getPicturesInMonth(action.year, action.month)
                println("Size is ${picturesInMonth.size}")
                emitUserAction(CalendarAction.GotPicturesInMonth(action.year, action.month, picturesInMonth))
            }

            is CalendarAction.JumpToYear -> actualState.copy(yearSelected = action.year).asResult()
            is CalendarAction.ResetToHome -> actualState.copy(yearSelected = null).asResult()
            CalendarAction.PopulateYears -> actualState withSideEffect {
                when (val result = LoadTreeUseCase(calendarRepository).invoke()) {
                    is LBResult.Success -> {
                        emitUserAction(CalendarAction.GotYears(result.successData.first))
                        emitUserAction(CalendarAction.GotMonthsOfYears(result.successData.second))
                    }

                    is LBResult.Failure<*> -> TODO()
                }
            }
        }
    }
}
