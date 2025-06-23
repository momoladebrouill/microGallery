package studio.lunabee.amicrogallery

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.serialization.decodeArguments
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer

@SuppressLint("RestrictedApi")
@OptIn(InternalSerializationApi::class)
@Composable
fun ResolveArgsToString(entry: NavBackStackEntry) {
    val routeObject: Any? = remember(entry) {
        var routeString = entry.destination.route?.substringBefore("/").orEmpty().substringBefore("?")
        val serializer = Class.forName(routeString).kotlin.serializer()
        val bundle = entry.arguments ?: Bundle()
        val typeMap = entry.destination.arguments.mapValues { it.value.type }
        serializer.decodeArguments(bundle, typeMap)
    }
    Text(
        text = "Current screen : ${routeObject.toString().substringAfterLast(".")}",
        style = MaterialTheme.typography.bodyMedium,
    )
}

@SuppressLint("RestrictedApi")
@Composable
fun DebugMenu(
    navHostController: NavHostController,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding(),
    ) {
        item {
            Text(
                text = "Debug menu 😘",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            val currentDestination = navHostController.currentBackStackEntryAsState()
            currentDestination.value?.let {
                ResolveArgsToString(it)
            }
        }
    }
}
