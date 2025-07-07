package studio.lunabee.amicrogallery.calendar

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.MMonth
import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.data.MicroPicture
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.data.SettingsData
import studio.lunabee.microgallery.android.data.YearPreview

sealed interface CalendarAction {

    object JumpToSettings : CalendarAction // Jump to settings screen
    object ResetToHome : CalendarAction // Jump to the year view

    object PopulateYears : CalendarAction // Get Years and month in years

    data class ShowPhoto(
        // Jump to photoViewer screen
        val pictureId: Long,
    ) : CalendarAction

    data class GotYears(
        // Got a db response for the list of different years
        val years: List<YearPreview>,
    ) : CalendarAction

    data class GotMonthsOfYears(
        // Got a db response for the months in the year X
        val monthsOfYears: Map<MYear, List<MMonth>>,
    ) : CalendarAction

    data class GotPicturesInMonth(
        // Got a db response for the list of pictures in month X year Y
        val year: MYear,
        val month: MMonth,
        val pictures: List<MicroPicture>,
    ) : CalendarAction

    // We first ask for changes in UI to update the showing list, then a db query is called
    data class AskForExpand(
        // Ask to show pictures of month X year Y
        val year: MYear,
        val month: MMonth,
    ) : CalendarAction

    data class JumpToYear(
        val year: MYear,
    ) : CalendarAction

}
