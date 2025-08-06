package studio.lunabee.microgallery.android.repository.datasource.remote

import studio.lunabee.microgallery.android.data.Directory
import studio.lunabee.microgallery.android.data.MYear

interface TreeRemoteDatasource {
    suspend fun getYears(): List<MYear>
    fun getYearDirs(years: List<MYear>): Flow<Directory>
}
