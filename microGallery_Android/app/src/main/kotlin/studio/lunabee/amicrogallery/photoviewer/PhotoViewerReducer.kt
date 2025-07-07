package studio.lunabee.amicrogallery.photoviewer

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.core.content.FileProvider
import coil3.Image
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult
import studio.lunabee.compose.presenter.withSideEffect
import studio.lunabee.microgallery.android.data.MicroPicture
import studio.lunabee.microgallery.android.data.Picture
import java.io.File

class PhotoViewerReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (PhotoViewerAction) -> Unit,
) : LBSingleReducer<PhotoViewerUiState, PhotoViewerNavScope, PhotoViewerAction>() {

    suspend fun downloadAndShareImage(context: Context, picture: MicroPicture, launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) {
        val loader = ImageLoader(context)
        val drawable : Image? = picture.highResPaths.fold(null) { acc, path ->
            if(acc == null) {
                val request = ImageRequest.Builder(context)
                    .data(path)
                    .build()
                val result = loader.execute(request)
                (result as? SuccessResult)?.image
            } else acc
        }

        val file = withContext(Dispatchers.IO) {
            val file = File(context.cacheDir, picture.name)
            file.outputStream().use { out ->
                val bitmap = (drawable as coil3.BitmapImage).bitmap
                bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 100, out)
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
        actualState: PhotoViewerUiState,
        action: PhotoViewerAction,
        performNavigation: (PhotoViewerNavScope.() -> Unit) -> Unit,
    ): ReduceResult<PhotoViewerUiState> {
        return when (action) {
            is PhotoViewerAction.FoundPicture -> actualState.copy(picture = action.picture!!).asResult()
            is PhotoViewerAction.SharePicture -> actualState.copy(loading = true) withSideEffect {
                downloadAndShareImage(action.context, actualState.picture!!, action.launcher)
            }

            PhotoViewerAction.StopLoading -> actualState.copy(loading = false).asResult()
        }
    }
}
