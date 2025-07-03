package studio.lunabee.amicrogallery.calendar

import studio.lunabee.microgallery.android.data.Picture
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
        val monthsOfYears: Map<String, List<String>>,
    ) : CalendarAction

    data class GotPicturesInMonth(
        // Got a db response for the list of pictures in month X year Y
        val year: String,
        val month: String,
        val pictures: List<Picture>,
    ) : CalendarAction

    // We first ask for changes in UI to update the showing list, then a db query is called
    data class AskForExpand(
        // Ask to show pictures of month X year Y
        val year: String,
        val month: String,
    ) : CalendarAction

    data class JumpToYear(
        val year: String,
    ) : CalendarAction
}
