package studio.lunabee.amicrogallery.calendar

import kotlinx.coroutines.CoroutineScope
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult
import studio.lunabee.compose.presenter.withSideEffect

class CalendarReducer(

    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (CalendarAction) -> Unit,
) : LBSingleReducer<CalendarUiState, CalendarNavScope, CalendarAction> () {

    override suspend fun reduce(
        actualState: CalendarUiState,
        action: CalendarAction,
        performNavigation: (CalendarNavScope.() -> Unit) -> Unit,
    ): ReduceResult<CalendarUiState> {
        return when(action){
            is CalendarAction.GotYears -> actualState.copy(years = action.years).asResult()
            is CalendarAction.GotMonthsOfYears -> actualState.copy(monthsOfYears = action.monthsOfYears).asResult()
            is CalendarAction.ForgetMYPhotos -> actualState.copy(
                photosOfMonth = actualState.photosOfMonth.filter { (pair, _) -> pair !=  Pair(action.year, action.month)},
                expandedMonths = actualState.expandedMonths - setOf(Pair(action.year, action.month))
            ).asResult()
            is CalendarAction.GotMY -> actualState.copy(
                photosOfMonth = actualState.photosOfMonth + ( Pair(action.year, action.month) to action.pictures),
            ).asResult()
            is CalendarAction.AskForExpand -> actualState.copy(
                expandedMonths = actualState.expandedMonths + Pair(action.year, action.month)
            ).asResult()

            is CalendarAction.AskForCollapse -> actualState.copy(
                expandedMonths = actualState.expandedMonths - Pair(action.year, action.month)
            ).asResult()

            is CalendarAction.ShowPhoto -> actualState withSideEffect {
                performNavigation {navigateToPhotoViewer(action.pictureId)}
            }
        }

    }
}
