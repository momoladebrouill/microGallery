package studio.lunabee.amicrogallery.bottomBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
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
import studio.lunabee.amicrogallery.calendar.CalendarDestination
import studio.lunabee.amicrogallery.core.ui.R
import studio.lunabee.amicrogallery.lastmonth.LastMonthDestination
import studio.lunabee.amicrogallery.untimed.UntimedDestination

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MicroGalleryBottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val currentBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
    Row(
        modifier = Modifier.fillMaxWidth().padding(PaddingValues(CoreSpacing.SpacingMedium)),
        horizontalArrangement = Arrangement.Center,
    ) {
        HorizontalFloatingToolbar(
            expanded = true,
            modifier = modifier,
        ) {
            NavBarButton(
                onClick = { navController.navigate(CalendarDestination) },
                icon = painterResource(R.drawable.calendar),
                description = stringResource(R.string.calendar_icon_button),
                activated = currentBackStackEntry?.destination?.hierarchy?.any { it.hasRoute(CalendarDestination::class) } == true,
            )
            NavBarButton(
                onClick = { navController.navigate(UntimedDestination) },
                icon = painterResource(R.drawable.not_time),
                description = stringResource(R.string.untimed_icon_button),
                activated = currentBackStackEntry?.destination?.hierarchy?.any { it.hasRoute(UntimedDestination::class) } == true,
            )
            NavBarButton(
                onClick = { navController.navigate(LastMonthDestination) },
                icon = painterResource(R.drawable.month_24px),
                description = stringResource(R.string.lastmonth_icon_button),
                activated = currentBackStackEntry?.destination?.hierarchy?.any { it.hasRoute(LastMonthDestination::class) } == true,
            )
        }
    }
}
