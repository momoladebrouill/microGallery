package studio.lunabee.microgallery.android.repository.impl

import studio.lunabee.amicrogallery.picture.PictureLocal
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.domain.calendar.CalendarRepository
import studio.lunabee.microgallery.android.repository.datasource.remote.TreeRemoteDatasource

class CalendarRepositoryImpl(
    private val treeRemoteDatasource: TreeRemoteDatasource,
    private val pictureLocal: PictureLocal,
) : CalendarRepository {

    override suspend fun getMonthsInYear(year: String): List<String> {
        return pictureLocal.getMonthsInYear(year = year)
    }

    override suspend fun getPicturesInMonth(year: String,
        month: String): List<Picture> {
        return pictureLocal.getPicturesInMonth(year, month)
    }

    override suspend fun getYears(): List<String> {
        return pictureLocal.getYears()
    }
}
