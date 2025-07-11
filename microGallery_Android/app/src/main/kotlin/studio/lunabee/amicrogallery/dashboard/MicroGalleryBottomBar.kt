package studio.lunabee.amicrogallery.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarColors
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import studio.lunabee.amicrogallery.android.core.ui.component.topappbar.NavBarButton
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreSpacing
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.colors
import studio.lunabee.amicrogallery.calendar.CalendarDestination
import studio.lunabee.amicrogallery.core.ui.R
import studio.lunabee.amicrogallery.lastmonth.LastMonthDestination
import studio.lunabee.amicrogallery.reorder.ReorderDestination

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MicroGalleryBottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val currentBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()

    // Show the bottom bar if any of the screens is displayed
    val isOn = listOf(CalendarDestination, LastMonthDestination, ReorderDestination).any { dest ->
        currentBackStackEntry?.destination?.hierarchy?.any { it.hasRoute(dest::class) } == true
    }

    AnimatedVisibility(
        visible = isOn,
        modifier = modifier,
    ) {
        HorizontalFloatingToolbar(
            expanded = true,
            colors = FloatingToolbarColors(
                fabContainerColor = colors.main,
                toolbarContainerColor = colors.main,
                toolbarContentColor = colors.onMain,
                fabContentColor = colors.onMain,
            ),
            modifier = Modifier.Companion.navigationBarsPadding(),
            expandedShadowElevation = CoreSpacing.SpacingSmall,
        ) {
            NavBarButton(
                onClick = { navController.navigate(CalendarDestination) },
                icon = painterResource(R.drawable.calendar),
                description = stringResource(R.string.calendar_icon_button),
                activated = currentBackStackEntry?.destination?.hierarchy?.any { it.hasRoute(CalendarDestination::class) } == true,
            )
            NavBarButton(
                onClick = { navController.navigate(LastMonthDestination) },
                icon = painterResource(R.drawable.month_24px),
                description = stringResource(R.string.lastmonth_icon_button),
                activated = currentBackStackEntry?.destination?.hierarchy?.any { it.hasRoute(LastMonthDestination::class) } == true,
            )
            NavBarButton(
                onClick = { navController.navigate(ReorderDestination) },
                icon = painterResource(R.drawable.joystick_24px),
                description = stringResource(R.string.lastmonth_icon_button),
                activated = currentBackStackEntry?.destination?.hierarchy?.any { it.hasRoute(ReorderDestination::class) } == true,
            )
        }
    }
}
