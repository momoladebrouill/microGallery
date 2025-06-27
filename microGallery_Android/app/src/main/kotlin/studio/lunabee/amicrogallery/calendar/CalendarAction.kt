package studio.lunabee.amicrogallery.calendar

import studio.lunabee.microgallery.android.domain.Node

sealed interface CalendarAction {
    class JumpToSettings : CalendarAction
    data class StopRefreshing(val foundNode: Node) : CalendarAction
}
