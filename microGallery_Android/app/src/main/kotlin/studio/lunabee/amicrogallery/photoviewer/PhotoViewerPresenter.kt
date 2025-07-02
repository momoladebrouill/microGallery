package studio.lunabee.amicrogallery.photoviewer

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.core.content.FileProvider
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.domain.photoviewer.PhotoViewerRepository
import java.io.File

import studio.lunabee.amicrogallery.app.R

class PhotoViewerPresenter(
    savedStateHandle: SavedStateHandle,
    val photoViewerRepository: PhotoViewerRepository,
) : LBSinglePresenter<PhotoViewerUiState, PhotoViewerNavScope, PhotoViewerAction>() {

    private val params: PhotoViewerDestination = savedStateHandle.toRoute()

    override val flows: List<Flow<PhotoViewerAction>> = listOf()

    override fun initReducer(): LBSingleReducer<PhotoViewerUiState, PhotoViewerNavScope, PhotoViewerAction> {
        return PhotoViewerReducer(
            coroutineScope = viewModelScope,
            emitUserAction = ::emitUserAction,
        )
    }

    init {
        getPictureById(params.pictureId)
    }

    fun getPictureById(pictureId: Long) {
        viewModelScope.launch {
            val pic: Picture = photoViewerRepository.getPictureById(pictureId)
            emitUserAction(PhotoViewerAction.FoundPicture(pic))
        }
    }

    // Thanks ChatGPT
    suspend fun downloadAndShareImage(context: Context, picture: Picture) {
        // Download with Coil 3
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data("http://92.150.239.130" + picture.fullResPath)
            .build()
        val result = loader.execute(request)
        val drawable = (result as? SuccessResult)?.image ?: return

        // Save to file
        val file = withContext(Dispatchers.IO) {
            val file = File(context.cacheDir, picture.name)
            file.outputStream().use { out ->
                val bitmap = (drawable as coil3.BitmapImage).bitmap
                bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 100, out)
            }
            file
        }

        // Get URI and share
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )

        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            type = "image/" + picture.name.substringAfter(".")
        }
        context.startActivity(Intent.createChooser(
            shareIntent,
            context.getString(R.string.share_picture, picture.name)
            )
        )

    }

    fun fireAction(action: PhotoViewerAction) {
        if (action is PhotoViewerAction.SharePicture) {
            viewModelScope.launch {
                downloadAndShareImage(
                    context = action.context, action.picture
                )
            }
        }
    }

    override fun getInitialState(): PhotoViewerUiState = PhotoViewerUiState(null)

    override val content: @Composable (PhotoViewerUiState) -> Unit = { PhotoViewerScreen(it, ::fireAction) }
}
