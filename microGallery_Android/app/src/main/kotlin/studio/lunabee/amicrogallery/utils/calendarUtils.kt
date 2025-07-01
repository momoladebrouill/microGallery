package studio.lunabee.amicrogallery.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringArrayResource
import studio.lunabee.amicrogallery.app.R

@Composable
fun getLabelName(value: String, months : Array<String>): String {
    val task = runCatching {
        val number = value.toInt()
        if (number < 13) {
            months[number - 1]
        } else {
            value
        }
    }
    return task.getOrElse { value }
}

fun getMonthFromKey(key: String): String {
    return key.substringAfter("/")
}