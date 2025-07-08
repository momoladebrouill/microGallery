package studio.lunabee.microgallery.android.repository.datasource.remote

import studio.lunabee.microgallery.android.data.Directory

interface TreeRemoteDatasource {
    suspend fun getRoot(): Directory
}
