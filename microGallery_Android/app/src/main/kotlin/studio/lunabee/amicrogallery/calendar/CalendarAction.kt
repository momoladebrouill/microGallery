package studio.lunabee.amicrogallery.calendar

sealed interface CalendarAction {

    sealed interface EmptyAction : CalendarAction

    data object FetchEventsEmptyScreen : EmptyAction
}
