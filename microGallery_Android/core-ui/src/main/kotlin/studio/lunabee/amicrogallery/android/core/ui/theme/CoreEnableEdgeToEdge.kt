package studio.lunabee.amicrogallery.android.core.ui.theme

import android.graphics.Color
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge

fun ComponentActivity.coreEnableEdgeToEdge(isDark: Boolean = true) {
    // Don't use SystemBarStyle.auto for navigation bar because it always add a scrim (cf doc)
    val navigationBarStyle = SystemBarStyle.dark(scrim = Color.TRANSPARENT)
    val statusBarStyle = if (isDark) {
        SystemBarStyle.dark(Color.TRANSPARENT)
    } else {
        SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
    }
    enableEdgeToEdge(
        statusBarStyle = statusBarStyle,
        navigationBarStyle = navigationBarStyle,
    )

    // For API29(Q) or higher and 3-button navigation,
    // the following code must be written to make the navigation color completely transparent.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) window.isNavigationBarContrastEnforced = false
}
