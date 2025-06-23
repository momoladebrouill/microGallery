package studio.lunabee.amicrogallery.calendar

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonShapes
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import studio.lunabee.amicrogallery.android.core.ui.component.image.MicroGalleryImage
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreSpacing
import studio.lunabee.amicrogallery.app.R

import studio.lunabee.microgallery.android.domain.Directory
import studio.lunabee.microgallery.android.domain.Node
import studio.lunabee.microgallery.android.domain.Picture

data class FlatNode(val node: Node, val depth: Int)
@OptIn(ExperimentalMaterial3ExpressiveApi::class)

@Composable
fun CalendarScreen(calendarUiState: CalendarUiState) {
    Log.d("TAG", "calendar screen is $calendarUiState")
    if (calendarUiState.rootNode == null) {
        DefaultScreen(calendarUiState)
    } else {
        val flatList = remember(calendarUiState.rootNode) { flattenTree(calendarUiState.rootNode) }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(flatList) { (node, depth) ->
                ShowFlatNode(node, depth)
            }
        }
    }
}

fun flattenTree(node: Node, depth: Int = 0): List<FlatNode> {
    val result = mutableListOf<FlatNode>()
    result.add(FlatNode(node, depth))
    if (node is Directory) {
        node.content.forEach { child ->
            result.addAll(flattenTree(child, depth + 1))
        }
    }
    return result
}

@Composable
fun ShowFlatNode(node: Node, depth: Int) {
    val paddingStart = (depth * 15).dp
    when (node) {
        is Directory -> {
            Text(
                text = getLabelName(node.name),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = paddingStart, top = 8.dp, bottom = 4.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
        is Picture -> {
            Text(
                text = getLabelName(node.fullPath ?: node.name),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 4.dp),
                style = MaterialTheme.typography.bodySmall
            )
            MicroGalleryImage(
                url = "http://92.150.239.130" + node.fullPath,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(PaddingValues(CoreSpacing.Bottom_row_spacing.dp)))
        }
    }
}

@Composable
fun getLabelName(value : String) : String {
    val number : Int
    try {
        number = value.toInt()
    } catch (e:NumberFormatException) {
        return value
    }
    if (number < 13) {
        return stringArrayResource(R.array.french_months)[number-1]
    }
    return value
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DefaultScreen(calendarUiState: CalendarUiState) {
    Column {
        Text(
            text = calendarUiState.textUiShown
        )
        Button(
            onClick = {},
            contentPadding = PaddingValues(0.dp),
            shapes = ButtonShapes(RectangleShape, RectangleShape)
        ) {
            MicroGalleryImage(
                url = "http://92.150.239.130/disque/photos/ranged/ryan.jpg",
                modifier = Modifier.fillMaxWidth(0.3f)
            )
        }

    }
}