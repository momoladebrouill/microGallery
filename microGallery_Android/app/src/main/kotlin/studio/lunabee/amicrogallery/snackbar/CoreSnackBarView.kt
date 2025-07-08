package studio.lunabee.amicrogallery.snackbar

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.touchlab.kermit.Logger
import org.koin.compose.viewmodel.koinViewModel
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme

@Composable
fun CoreSnackBarView(
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    snackBarViewModel: SnackBarViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    var lastTypeShow by remember { mutableStateOf(SnackbarType.Default) }
    val snackBarData by snackBarViewModel.shownSnackBar.collectAsStateWithLifecycle()

    LaunchedEffect(snackBarData) {
        snackBarData?.let { data ->
            lastTypeShow = data.type
            snackBarHostState.showSnackBar(
                data = data,
                context = context,
            )
            snackBarViewModel.consumeSnackBar()
        }
    }
    Box(
        modifier = Modifier
            .statusBarsPadding(),
    ) {
        SnackbarHost(
            modifier = Modifier
                .align(Alignment.BottomStart),
            hostState = snackBarHostState,
        ) { data ->
            when (lastTypeShow) {
                SnackbarType.Default -> DefaultSnackBar(snackbarData = data)
                SnackbarType.Error -> ErrorSnackBar(snackbarData = data)
            }
        }
    }
}

@Composable
fun DefaultSnackBar(
    snackbarData: SnackbarData,
) {
    Snackbar(
        snackbarData = snackbarData,
        containerColor = MicroGalleryTheme.colors.background,
        contentColor = MicroGalleryTheme.colors.onBackground,
        actionColor = MicroGalleryTheme.colors.main,
        actionContentColor = MicroGalleryTheme.colors.onMain,
    )
}

@Composable
fun ErrorSnackBar(
    snackbarData: SnackbarData,
) {
    Snackbar(
        snackbarData = snackbarData,
        containerColor = MicroGalleryTheme.colors.second,
        contentColor = MicroGalleryTheme.colors.onMain,
    )
}

private suspend fun SnackbarHostState.showSnackBar(data: CoreSnackBarData, context: Context) {
    when (
        this.showSnackbar(
            message = data.message.string(context),
            actionLabel = data.action?.actionLabel?.string(context),
            duration = SnackbarDuration.Short,
        )
    ) {
        SnackbarResult.ActionPerformed -> {
            val action = data.action
            when (action) {
                is SnackbarAction.Default -> {
                    action.onClick()
                    action.onDismiss()
                }

                else -> Logger.e("Unexpected snackBar action performed $data")
            }
        }

        SnackbarResult.Dismissed -> {
            val action = data.action
            when (action) {
                is SnackbarAction.Default -> {
                    action.onDismiss()
                }

                else -> {
                    /* no-op  : No need to call the onDismiss method*/
                }
            }
        }
    }
}
