package studio.lunabee.amicrogallery.utils

import android.content.Context
import android.content.pm.PackageManager
import studio.lunabee.amicrogallery.settings.SettingsAction
import studio.lunabee.microgallery.android.data.SettingsData

fun Context.getAppVersion(): String {
    return try {
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        packageInfo.versionName ?: "Unknown"
    } catch (e: PackageManager.NameNotFoundException) {
        "Unknown"
    }
}
