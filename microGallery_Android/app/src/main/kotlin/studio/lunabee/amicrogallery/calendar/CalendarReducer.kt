package studio.lunabee.amicrogallery.calendar

import com.lunabee.lbcore.model.LBResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult
import studio.lunabee.compose.presenter.withSideEffect
import studio.lunabee.microgallery.android.data.MMonth
import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.data.YearPreview
import studio.lunabee.microgallery.android.domain.calendar.CalendarRepository
import studio.lunabee.microgallery.android.domain.calendar.usecase.LoadPartialTreeUseCase
import studio.lunabee.microgallery.android.domain.calendar.usecase.ObserveYearPreviewsUseCase

class CalendarReducer(

    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (CalendarAction) -> Unit,
    val calendarRepository: CalendarRepository,
    val yearPreviews : Flow<List<YearPreview>>,
    val monthsInYears : Flow<Map<MYear, List<MMonth>>>
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
                picturesInMonth.collect {
                    emitUserAction(CalendarAction.GotPicturesInMonth(action.year, action.month, it))
                }
            }

            is CalendarAction.JumpToYear -> actualState.copy(yearSelected = action.year).asResult()

            is CalendarAction.ResetToHome -> actualState.copy(yearSelected = null).asResult()
            CalendarAction.PopulateYears -> actualState withSideEffect {
                yearPreviews.collect {
                    emitUserAction(CalendarAction.GotYears(it))
                }
            }
        }
    }
}
