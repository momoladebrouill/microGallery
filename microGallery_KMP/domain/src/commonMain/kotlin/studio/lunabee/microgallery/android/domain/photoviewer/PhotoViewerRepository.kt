package studio.lunabee.microgallery.android.domain.photoviewer

import studio.lunabee.microgallery.android.data.Picture

interface PhotoViewerRepository {
    suspend fun getPictureById(id : Long) : Picture
}