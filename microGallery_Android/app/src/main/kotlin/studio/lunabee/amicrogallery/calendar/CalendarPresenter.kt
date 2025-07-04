package studio.lunabee.amicrogallery.calendar

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.microgallery.android.data.MMonth
import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.data.SettingsData
import studio.lunabee.microgallery.android.domain.calendar.CalendarRepository
import studio.lunabee.microgallery.android.domain.calendar.usecase.LoadPartialTreeUseCase
import studio.lunabee.microgallery.android.domain.calendar.usecase.ObserveYearPreviewsUseCase
import studio.lunabee.microgallery.android.domain.settings.usecase.ObserveSettingsUseCase

class CalendarPresenter(
    val calendarRepository: CalendarRepository,
    val observeYearPreviewsUseCase: ObserveYearPreviewsUseCase,
    val loadPartialTreeUseCase: LoadPartialTreeUseCase
) : LBSinglePresenter<CalendarUiState, CalendarNavScope, CalendarAction>() {

    private val yearPreviews = observeYearPreviewsUseCase().map {CalendarAction.GotYears(it)}
    val monthsInYears = loadPartialTreeUseCase().map { CalendarAction.GotMonthsOfYears(it) }

    override val flows: List<Flow<CalendarAction>> = listOf(
        yearPreviews,
        monthsInYears
    )

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
        return CalendarReducer(
            coroutineScope = viewModelScope,
            emitUserAction = ::emitUserAction,
            calendarRepository = calendarRepository,
            yearPreviews = TODO(),
            monthsInYears = TODO(),
        )
    }

    init {
        emitUserAction(CalendarAction.PopulateYears)
    }

    override val content: @Composable ((CalendarUiState) -> Unit) = { CalendarScreen(it) }
}
