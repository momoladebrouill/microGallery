package studio.lunabee.amicrogallery.calendar

import kotlinx.coroutines.flow.Flow
import studio.lunabee.compose.presenter.PresenterUiState
import studio.lunabee.microgallery.android.data.MMonth
import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.data.SettingsData
import studio.lunabee.microgallery.android.data.YearPreview

data class CalendarUiState(
    val years: List<YearPreview>,
    val monthsOfYears: Map<MYear, Flow<List<MMonth>>>,
    val photosOfMonth: Map<Pair<MYear, MMonth>, List<Picture>>,
    val expandedMonths: Set<Pair<MYear, MMonth>>,
    val yearSelected: MYear? = null,
    val jumpToSettings: () -> Unit,
    val jumpToYear: (MYear) -> Unit,
    val resetToHome: () -> Unit,
    val showPhoto: (Long) -> Unit,
    val askForExpand: (MYear, MMonth) -> Unit,

) : PresenterUiState
