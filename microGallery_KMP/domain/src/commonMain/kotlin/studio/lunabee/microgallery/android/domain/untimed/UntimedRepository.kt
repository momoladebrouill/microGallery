package studio.lunabee.microgallery.android.domain.untimed

import studio.lunabee.microgallery.android.data.Picture

interface UntimedRepository {
    fun getPicturesUntimed() : List<Picture>
}