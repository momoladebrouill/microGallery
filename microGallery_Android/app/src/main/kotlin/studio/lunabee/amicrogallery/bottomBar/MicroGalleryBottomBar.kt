package studio.lunabee.amicrogallery.bottomBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarColors
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
import java.time.LocalDate

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MicroGalleryBottomBar(
    navController: NavHostController,
    modifier : Modifier = Modifier
) {
    val currentBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
    val upPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    val isOn = listOf(CalendarDestination, UntimedDestination, LastMonthDestination).map {
        dest -> currentBackStackEntry?.destination?.hierarchy?.any { it.hasRoute(dest::class) } == true
    }.contains(true)

    AnimatedVisibility(
        visible = isOn,
        modifier = modifier
    ) {

        HorizontalFloatingToolbar(
            expanded = true,
            colors = FloatingToolbarColors(
                fabContainerColor = MaterialTheme.colorScheme.primaryContainer,
                toolbarContainerColor = ButtonDefaults.buttonColors().containerColor,
                toolbarContentColor = contentColorFor(MaterialTheme.colorScheme.primaryContainer),
                fabContentColor = contentColorFor(MaterialTheme.colorScheme.onSecondaryContainer),
            ),
            modifier = Modifier.navigationBarsPadding(),
            expandedShadowElevation = CoreSpacing.SpacingSmall,
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
