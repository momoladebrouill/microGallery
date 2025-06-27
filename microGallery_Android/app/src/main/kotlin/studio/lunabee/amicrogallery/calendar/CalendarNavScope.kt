package studio.lunabee.amicrogallery.calendar

interface CalendarNavScope {
    val navigateToMicroYear: (Int) -> Unit
    val navigateToPhotoViewer : (Long) -> Unit
}
