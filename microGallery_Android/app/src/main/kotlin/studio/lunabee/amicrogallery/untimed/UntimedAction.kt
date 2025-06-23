package studio.lunabee.amicrogallery.untimed

sealed interface UntimedAction {

    sealed interface EmptyAction : UntimedAction

    data object FetchEventsEmptyScreen : EmptyAction
}
