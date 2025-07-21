package studio.lunabee.microgallery.android.repository.impl

import studio.lunabee.amicrogallery.picture.PictureLocal
import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.data.MicroPicture
import studio.lunabee.microgallery.android.domain.reorder.ReorderRepository

class ReorderRepositoryImpl(
    val pictureLocal: PictureLocal,
) : ReorderRepository {
    override suspend fun getRandomInYear(year: MYear): MicroPicture {
        return pictureLocal.getRandomInYear(year)
    }
}
