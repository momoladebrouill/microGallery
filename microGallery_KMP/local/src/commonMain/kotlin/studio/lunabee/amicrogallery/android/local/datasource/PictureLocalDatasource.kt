package studio.lunabee.amicrogallery.android.local.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import studio.lunabee.amicrogallery.android.local.dao.PictureDao
import studio.lunabee.amicrogallery.android.local.entity.PictureEntity
import studio.lunabee.amicrogallery.picture.PictureLocal
import studio.lunabee.microgallery.android.data.MMonth
import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.data.MicroPicture
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.data.YearPreview
import studio.lunabee.microgallery.android.domain.settings.usecase.ObserveSettingsUseCase

class PictureLocalDatasource(
    private val pictureDao: PictureDao,
    private val observeSettingsUseCase: ObserveSettingsUseCase,
) : PictureLocal {

    val settingsData = observeSettingsUseCase()
    private var _lastOrder: Double = 0.0

    override suspend fun insertPictures(pictures: List<Picture>) {
        val pictureEntities = pictures.map { picture ->
            _lastOrder = _lastOrder + 1.0
            PictureEntity.fromPicture(picture, _lastOrder)
        }
        pictureDao.insertPicturesEntities(pictureEntities)
    }

    override fun getYearPreviews(): Flow<List<YearPreview>> {
        val years: Flow<List<MYear>> = pictureDao.getYears()

        return years.combine(settingsData) { years, settingsData ->
            years.map { year ->
                YearPreview(
                    year = year,
                    qty = pictureDao.getQtyInYear(year).first(),
                    picturePreview = pictureDao.getRandomPictureInYear(year).first().toMicroPicture(settingsData),
                )
            }
        }
    }

    override suspend fun freshStart() {
        pictureDao.freshStart()
    }

    override fun getMonthsInYear(year: MYear): Flow<List<MMonth>> {
        return pictureDao.monthsInYear(year)
    }

    override fun getPicturesInMonth(
        year: MYear,
        month: MMonth,
    ): Flow<List<MicroPicture>> {
        return pictureDao.picturesInMonth(year, month).combine(settingsData) { pictures, settings ->
            pictures.map { it.toMicroPicture(settings) }
        }
    }

    override fun getPicturesUntimed(): Flow<List<MicroPicture>> {
        return pictureDao.picturesUntimed().combine(settingsData) { pictureEntities, data ->
            pictureEntities.map { it.toMicroPicture(data) }
        }
    }

    override fun getPictureById(id: Long): Flow<MicroPicture> {
        return pictureDao.pictureEntityFromId(id = id).combine(settingsData) { pictureEntity, data ->
            pictureEntity.toMicroPicture(data)
        }
    }

    override suspend fun getOrderById(id: Long): Float {
        return pictureDao.getOrderById(id)
    }

    override suspend fun getFirstPictureBefore(order: Float): MicroPicture {
        return pictureDao.getFirstPictureBefore(order).toMicroPicture(settingsData.first())
    }

    override suspend fun getFirstPictureAfter(order: Float): MicroPicture {
        return pictureDao.getFirstPictureAfter(order).toMicroPicture(settingsData.first())
    }

    override suspend fun isDbEmpty(): Boolean {
        return !pictureDao.isThereAnyPicture()
    }
}
