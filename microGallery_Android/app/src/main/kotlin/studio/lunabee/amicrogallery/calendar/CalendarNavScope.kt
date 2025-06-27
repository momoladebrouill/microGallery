package studio.lunabee.amicrogallery.calendar

interface CalendarNavScope {
    val navigateToPhotoViewer: (Long) -> Unit
    val navigateToSettings: () -> Unit
}
