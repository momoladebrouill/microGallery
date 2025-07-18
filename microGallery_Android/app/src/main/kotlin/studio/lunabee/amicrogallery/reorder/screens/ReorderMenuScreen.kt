package studio.lunabee.amicrogallery.reorder.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.colors
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.typography
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.amicrogallery.reorder.ReorderUiState
import studio.lunabee.amicrogallery.utils.isInt

@Composable
fun ReorderMenuScreen(uiState: ReorderUiState.ReorderMenuUiState) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.align(Alignment.Center).fillMaxWidth().fillMaxHeight(0.4f)
        ) {
            Text(
                text = stringResource(R.string.reorder_game),
                style = typography.header,
                color = colors.onBackground
            )
            Text(
                text = stringResource(R.string.reorder_game_explain),
                style = typography.body,
                color = colors.onBackground,
                textAlign = TextAlign.Center
            )

            var numberStr by remember { mutableStateOf("5") }

            TextField(
                value = numberStr,
                onValueChange = {
                    numberStr = it
                    if(it.isInt()){
                        uiState.setQty(it.toInt())
                    }
                                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                label = { Text("Number of pictures", color = colors.onMain) }
            )

            Button(
                onClick = uiState.jumpToGaming
            ) {
                Text(
                    text = stringResource(R.string.start),
                    color = colors.onBackground
                )
            }
        }

    }
}