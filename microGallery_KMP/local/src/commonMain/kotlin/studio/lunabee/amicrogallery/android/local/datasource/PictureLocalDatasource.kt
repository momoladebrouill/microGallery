package studio.lunabee.amicrogallery.android.local.datasource

import studio.lunabee.amicrogallery.android.local.dao.PictureDao
import studio.lunabee.amicrogallery.android.local.entity.PictureEntity
import studio.lunabee.amicrogallery.picture.PictureLocal
import studio.lunabee.microgallery.android.data.MMonth
import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.data.YearPreview

class PictureLocalDatasource(
    private val pictureDao: PictureDao,
) : PictureLocal {

    override suspend fun insertPictures(pictures: List<Picture>) {
        pictureDao.insertPicturesEntities(pictures.map(PictureEntity::fromPicture))
    }

    override suspend fun getYearPreviews(): List<YearPreview> {
        val years: List<MYear> = pictureDao.getYears()
        return years.map { year ->
            YearPreview(
                year = year,
                qty = pictureDao.getQtyInYear(year),
                picturePreview = pictureDao.getRandomPictureInYear(year).toPicture(),
            )
        }
    }

    override suspend fun freshStart() {
        pictureDao.freshStart()
    }

    override suspend fun getMonthsInYear(year: MYear): List<MMonth> {
        return pictureDao.getMonthsInYear(year)
    }

    override suspend fun getPicturesInMonth(
        year: MYear,
        month: MMonth,
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
