package studio.lunabee.amicrogallery.calendar

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CalendarScreen(calendarUiState: CalendarUiState) {
    Text(
        text = calendarUiState.textUiShown,
    )
}
