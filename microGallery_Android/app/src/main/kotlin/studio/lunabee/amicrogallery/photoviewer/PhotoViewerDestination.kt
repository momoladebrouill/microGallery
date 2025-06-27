package studio.lunabee.amicrogallery.photoviewer

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
data class PhotoViewerDestination(
    val pictureId : Long
) {
    fun composable(navGraphBuilder: NavGraphBuilder, navScope: PhotoViewerNavScope) {
        navGraphBuilder.composable<PhotoViewerDestination> {
            val presenter: PhotoViewerPresenter = koinViewModel()
            presenter.invoke(navScope)
        }
    }
}