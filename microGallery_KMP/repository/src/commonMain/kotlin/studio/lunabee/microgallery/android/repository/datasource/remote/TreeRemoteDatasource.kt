package studio.lunabee.microgallery.android.repository.datasource.remote

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.Directory
import studio.lunabee.microgallery.android.data.Node

interface TreeRemoteDatasource {

    fun getRoot(): Flow<Directory>
}
