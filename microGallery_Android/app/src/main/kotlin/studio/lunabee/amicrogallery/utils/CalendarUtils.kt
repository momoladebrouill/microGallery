package studio.lunabee.amicrogallery.utils

import androidx.compose.foundation.lazy.LazyListItemInfo

fun getMonthName(value: String, monthArray : Array<String>): String {
    val task = runCatching {
        val number = value.toInt()
        if (number < 13) {
            monthArray[number - 1]
        } else {
            value
        }
    }
    return task.getOrElse { value }
}

fun getMonthFromKey(key: String): String {
    return key.substringAfter("/")
}

fun calculateInterpolationValue(density: Float, currentShownYear: LazyListItemInfo?): Float {
    val currentFirstHeightInDp = (currentShownYear?.size ?: 1) / density
    val currentFirstOffsetInDp = -(currentShownYear?.offset ?: 0) / density
    return currentFirstOffsetInDp / currentFirstHeightInDp
}
