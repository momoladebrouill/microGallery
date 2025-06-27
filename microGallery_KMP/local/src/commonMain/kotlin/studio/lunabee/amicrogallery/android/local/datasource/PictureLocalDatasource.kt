package studio.lunabee.amicrogallery.android.local.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import studio.lunabee.amicrogallery.android.local.dao.PictureDao
import studio.lunabee.amicrogallery.android.local.entity.PictureEntity
import studio.lunabee.amicrogallery.picture.PictureLocal
import studio.lunabee.microgallery.android.data.Picture

class PictureLocalDataSource(
private val pictureDao: PictureDao,
) : PictureLocal {
    override suspend fun insertPictures(pictures: List<Picture>) {
        pictureDao.insertPicturesEntities(pictures.map(PictureEntity::fromPicture))
    }

    override fun getPictures(): Flow<List<Picture>> {
        return pictureDao.getPicturesEntities().map { list -> list.map { it.toPicture() } }
    }

    override suspend fun insertPicture(picture: Picture) {
        pictureDao.insertPicturesEntity(PictureEntity.fromPicture(picture))
    }

    override suspend fun getYears(): List<String> {
        return pictureDao.getYears()
    }

    override suspend fun freshStart() {
        pictureDao.freshStart()
    }

    override suspend fun getMonthsInYear(year: String) : List<String>{
        return pictureDao.getMonthsInYear(year)
    }

    override suspend fun getPicturesInMonth(year: String,
        month: String): List<Picture> {
        return pictureDao.getPicturesInMonth(year,month).map(PictureEntity::toPicture)
    }

    override suspend fun getPicturesUntimed(): List<Picture> {
        return pictureDao.getPicturesUntimed().map( PictureEntity::toPicture )
    }
}