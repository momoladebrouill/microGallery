package studio.lunabee.amicrogallery.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import studio.lunabee.amicrogallery.core.ui.R

@Composable
fun CalendarScreen(calendarUiState: CalendarUiState, onAction: (CalendarAction) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "Welcome to Calendar Screen",
        )
        IconButton(onClick = { onAction(CalendarAction.JumpToSettings()) }) {
            Icon(
                painterResource(R.drawable.settings_24px),
                contentDescription = "Settings menu",
            )
        }
    }
}
