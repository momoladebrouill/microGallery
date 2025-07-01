package studio.lunabee.amicrogallery.calendar

import studio.lunabee.amicrogallery.calendar.displayed.Display
import studio.lunabee.amicrogallery.calendar.displayed.MonthDisplay
import studio.lunabee.amicrogallery.calendar.displayed.PhotoDisplay
import studio.lunabee.compose.presenter.PresenterUiState
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.data.YearPreview

data class CalendarUiState(
    val years: List<YearPreview>,
    val monthsOfYears: Map<String, List<String>>,
    val photosOfMonth: Map<Pair<String, String>, List<Picture>>,
    val expandedMonths: Set<Pair<String, String>>,
    val yearSelected : String? = null
) : PresenterUiState
