package studio.lunabee.amicrogallery.android.local.datasource

import studio.lunabee.amicrogallery.android.local.dao.PictureDao
import studio.lunabee.amicrogallery.android.local.entity.PictureEntity
import studio.lunabee.amicrogallery.picture.PictureLocal
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.data.YearPreview

class PictureLocalDatasource(
    private val pictureDao: PictureDao,
) : PictureLocal {

    override suspend fun insertPictures(pictures: List<Picture>) {
        pictureDao.insertPicturesEntities(pictures.map(PictureEntity::fromPicture))
    }

    override suspend fun getYearPreviews(): List<YearPreview> {
        val years : List<String> = pictureDao.getYears()
        return years.map { year -> YearPreview(
            year = year,
            qty = pictureDao.getQtyInYear(year),
            picturePreview = pictureDao.getRandomPictureInYear(year).toPicture()
        ) }
    }

    override suspend fun freshStart() {
        pictureDao.freshStart()
    }

    override suspend fun getMonthsInYear(year: String): List<String> {
        return pictureDao.getMonthsInYear(year)
    }

    override suspend fun getPicturesInMonth(
        year: String,
        month: String,
    ): List<Picture> {
        return pictureDao.getPicturesInMonth(year, month).map(PictureEntity::toPicture)
    }

    override suspend fun getPicturesUntimed(): List<Picture> {
        return pictureDao.getPicturesUntimed().map(PictureEntity::toPicture)
    }

    override suspend fun getPictureById(id: Long): Picture {
        return pictureDao.getPictureEntityFromId(id = id).toPicture()
    }
}
