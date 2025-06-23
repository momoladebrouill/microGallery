package studio.lunabee.amicrogallery.lastmonth

sealed interface LastMonthAction {

    sealed interface EmptyAction : LastMonthAction

    data object FetchEventsEmptyScreen : EmptyAction
}
