package studio.lunabee.amicrogallery.calendar

import studio.lunabee.microgallery.android.domain.Node

sealed interface CalendarAction {

    data object Beginning : CalendarAction

    data class StopRefreshing(val foundNode: Node) : CalendarAction
}
