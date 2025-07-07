package studio.lunabee.amicrogallery.photoviewer

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import studio.lunabee.compose.presenter.PresenterUiState
import studio.lunabee.microgallery.android.data.Picture

data class PhotoViewerUiState( // picture is not here at the beginning, it must be found in db
    val picture: Picture?,
    val loading: Boolean = false,
    val share: (Context, ManagedActivityResultLauncher<Intent, ActivityResult>) -> Unit,
    val stopLoading: () -> Unit,
) : PresenterUiState
