package studio.lunabee.amicrogallery.calendar

import studio.lunabee.microgallery.android.data.Picture

sealed interface CalendarAction {
    data class GotYears(
        val years: List<String>
    ) : CalendarAction

    data class GotMonthsOfYears(
        val monthsOfYears: Map<String, List<String>>
    ) : CalendarAction

    data class GotMY(
        val month: String,
        val year: String,
        val pictures: List<Picture>
    ) : CalendarAction

    data class ForgetMYPhotos(
        val month: String,
        val year: String
    ) : CalendarAction

    data class AskForExpand(
        val month: String,
        val year : String
    ) : CalendarAction

    data class AskForCollapse(
        val month: String,
        val year : String
    ) : CalendarAction
}

