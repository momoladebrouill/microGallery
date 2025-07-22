package studio.lunabee.amicrogallery.utils

import studio.lunabee.microgallery.android.data.MMonth

fun getMonthName(value: MMonth, monthArray: Array<String>): String {
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
