package studio.lunabee.microgallery.android.repository.impl

import kotlinx.coroutines.flow.Flow
import studio.lunabee.amicrogallery.picture.PictureLocal
import studio.lunabee.microgallery.android.data.Node
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.domain.calendar.CalendarRepository
import studio.lunabee.microgallery.android.repository.datasource.remote.TreeRemoteDatasource

class CalendarRepositoryImpl(
    private val treeRemoteDatasource: TreeRemoteDatasource,
    private val pictureLocal: PictureLocal,
) : CalendarRepository {

    override suspend fun getMonthsInYear(year: Int): List<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun getPicturesInMonth(year: Int, month: Int): List<Picture> {
        TODO("Not yet implemented")
    }

    override suspend fun getYears(): Flow<List<Int>> {
        return pictureLocal.getYears()
    }
}
