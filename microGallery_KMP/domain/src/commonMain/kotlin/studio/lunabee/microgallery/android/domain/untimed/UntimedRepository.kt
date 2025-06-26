package studio.lunabee.microgallery.android.domain.untimed

import studio.lunabee.microgallery.android.data.Picture

interface UntimedRepository {
    suspend fun getPicturesUntimed() : List<Picture>
}