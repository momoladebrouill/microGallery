package studio.lunabee.amicrogallery.lastmonth

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LastMonthScreen(lastMonthUiState: LastMonthUiState) {
    Text(
        text = lastMonthUiState.textUiShown,
    )
}
