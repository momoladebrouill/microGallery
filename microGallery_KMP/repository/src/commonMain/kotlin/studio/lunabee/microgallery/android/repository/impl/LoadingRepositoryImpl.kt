package studio.lunabee.microgallery.android.repository.impl

import kotlinx.coroutines.flow.Flow
import studio.lunabee.amicrogallery.picture.PictureLocal
import studio.lunabee.microgallery.android.data.Directory
import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.domain.loading.LoadingRepository
import studio.lunabee.microgallery.android.repository.datasource.remote.TreeRemoteDatasource

class LoadingRepositoryImpl(
    val treeRemoteDatasource: TreeRemoteDatasource,
    val pictureLocal: PictureLocal,
) : LoadingRepository {
    override suspend fun getYears(): List<MYear> {
        return treeRemoteDatasource.getYears()
    }

    override fun getYearsAsFlow(years: List<MYear>): Flow<Directory> {
        return treeRemoteDatasource.getYearDirs(years)
    }

    override fun yearsInDb(): Flow<List<MYear>> {
        return pictureLocal.yearList()
    }

    override suspend fun pictureDbFreshStart() {
        pictureLocal.freshStart()
    }

    override suspend fun isPictureDbEmpty(): Boolean {
        return pictureLocal.isDbEmpty()
    }

    override suspend fun savePicturesInDb(pictures: List<Picture>) {
        pictureLocal.insertPictures(pictures)
    }

    override fun setBaseRemoteUrl(url: String) {
        treeRemoteDatasource.setRemoteBaseUrl(url)
    }
}
