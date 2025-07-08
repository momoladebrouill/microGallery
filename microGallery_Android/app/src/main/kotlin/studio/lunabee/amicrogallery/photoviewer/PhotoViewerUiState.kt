package studio.lunabee.amicrogallery.photoviewer

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.foundation.pager.PagerState
import studio.lunabee.compose.presenter.PresenterUiState
import studio.lunabee.microgallery.android.data.MicroPicture

sealed interface PhotoViewerUiState : PresenterUiState {
    object Waiting : PhotoViewerUiState
    data class HasPicture(
        val picture: MicroPicture,
        val neighbors: Pair<MicroPicture, MicroPicture>,
        val loading: Boolean = false,
        val pagerState: PagerState,
        val share: (Context, ManagedActivityResultLauncher<Intent, ActivityResult>) -> Unit,
        val stopLoading: () -> Unit,
        val switchTo: (Long) -> Unit,
    ) : PhotoViewerUiState
}
