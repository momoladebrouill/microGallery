package studio.lunabee.amicrogallery.photoviewer.reducers

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.core.content.FileProvider
import coil3.BitmapImage
import coil3.Image
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.amicrogallery.photoviewer.PhotoViewerAction
import studio.lunabee.amicrogallery.photoviewer.PhotoViewerNavScope
import studio.lunabee.amicrogallery.photoviewer.PhotoViewerUiState
import studio.lunabee.compose.presenter.LBReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult
import studio.lunabee.compose.presenter.withSideEffect
import studio.lunabee.microgallery.android.data.MicroPicture
import java.io.File

class PhotoViewerHasPhotoReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (PhotoViewerAction) -> Unit,
) : LBReducer<
    PhotoViewerUiState.HasPicture,
    PhotoViewerUiState,
    PhotoViewerNavScope,
    PhotoViewerAction,
    PhotoViewerAction.HasPictureAction,
    >() {

    suspend fun downloadAndShareImage(
        context: Context,
        picture: MicroPicture,
        launcher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    ) {
        val loader = ImageLoader(context)
        val drawable: Image? = picture.highResPaths.fold(null) { acc, path ->
            if (acc == null) {
                val request = ImageRequest.Builder(context)
                    .data(path)
                    .build()
                val result = loader.execute(request)
                (result as? SuccessResult)?.image
            } else {
                acc
            }
        }

        val file = withContext(Dispatchers.IO) {
            val file = File(context.cacheDir, picture.name)
            file.outputStream().use { out ->
                val bitmap = (drawable as BitmapImage).bitmap
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }
            file
        }

        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)

        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            type = "image/" + picture.name.substringAfter(".") // get the image format
        }

        launcher.launch(Intent.createChooser(shareIntent, context.getString(R.string.share_picture, picture.name)))
    }

    override suspend fun reduce(
        actualState: PhotoViewerUiState.HasPicture,
        action: PhotoViewerAction.HasPictureAction,
        performNavigation: (PhotoViewerNavScope.() -> Unit) -> Unit,
    ): ReduceResult<PhotoViewerUiState> {
        return when (action) {
            is PhotoViewerAction.SharePicture -> actualState.copy(loading = true) withSideEffect {
                downloadAndShareImage(action.context, actualState.picture, action.launcher)
            }

            PhotoViewerAction.StopLoading -> actualState.copy(loading = false).asResult()
        }
    }

    override fun filterAction(action: PhotoViewerAction): Boolean {
        return action is PhotoViewerAction.HasPictureAction
    }

    override fun filterUiState(actualState: PhotoViewerUiState): Boolean {
        return actualState is PhotoViewerUiState.HasPicture
    }
}
