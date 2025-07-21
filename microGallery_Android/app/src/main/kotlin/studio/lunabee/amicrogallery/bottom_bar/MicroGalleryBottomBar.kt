package studio.lunabee.amicrogallery.bottom_bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarColors
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.koin.compose.viewmodel.koinViewModel
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
    bottomBarViewModel: BottomBarViewModel = koinViewModel()
) {
    val currentBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()

    val manager = bottomBarViewModel.bottomBarManager

    val shown by manager.shown.collectAsStateWithLifecycle()
    var isOn by remember { mutableStateOf(true) }

    LaunchedEffect(shown) { isOn = shown }

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
