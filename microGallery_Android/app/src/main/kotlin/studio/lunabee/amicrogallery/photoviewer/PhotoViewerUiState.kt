package studio.lunabee.amicrogallery.photoviewer

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import studio.lunabee.compose.presenter.PresenterUiState
import studio.lunabee.microgallery.android.data.MicroPicture

data class PhotoViewerUiState(

    val picture: MicroPicture?,
    val loading: Boolean = false,
    val share: (Context, ManagedActivityResultLauncher<Intent, ActivityResult>) -> Unit,
    val stopLoading: () -> Unit,
) : PresenterUiState
