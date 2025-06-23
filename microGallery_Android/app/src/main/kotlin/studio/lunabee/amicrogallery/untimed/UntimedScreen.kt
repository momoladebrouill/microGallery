package studio.lunabee.amicrogallery.untimed

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun UntimedScreen(untimedUiState: UntimedUiState) {
    Text(
        text = untimedUiState.textUiShown,
    )
}
