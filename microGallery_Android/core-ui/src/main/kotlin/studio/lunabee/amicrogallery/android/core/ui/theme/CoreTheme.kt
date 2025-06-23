package studio.lunabee.amicrogallery.android.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

val LocalCoreColor: ProvidableCompositionLocal<CoreColorTheme> = staticCompositionLocalOf { error("no provided") }
val LocalCoreRadius: ProvidableCompositionLocal<CoreRadius> = staticCompositionLocalOf { error("no provided") }
val LocalCoreSpacing: ProvidableCompositionLocal<CoreSpacing> = staticCompositionLocalOf { error("no provided") }
val LocalCoreTypography: ProvidableCompositionLocal<CoreTypography> = staticCompositionLocalOf { error("no provided") }

@Composable
fun CoreTheme(
    content: @Composable () -> Unit,
) {
    val colors = if (isSystemInDarkTheme()) CoreColorDarkTheme else CoreColorLightTheme
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme(),
    ) {
        CompositionLocalProvider(
            LocalCoreColor provides colors,
            LocalCoreRadius provides CoreRadius,
            LocalCoreSpacing provides CoreSpacing,
            LocalCoreTypography provides CoreTypography,
            content = content,
        )
    }
}

object CoreTheme {
    val colors: CoreColorTheme
        @Composable
        get() = LocalCoreColor.current

    val typography: CoreTypography
        @Composable
        get() = LocalCoreTypography.current

    val spacing: CoreSpacing
        @Composable
        get() = LocalCoreSpacing.current

    val radius: CoreRadius
        @Composable
        get() = LocalCoreRadius.current
}
