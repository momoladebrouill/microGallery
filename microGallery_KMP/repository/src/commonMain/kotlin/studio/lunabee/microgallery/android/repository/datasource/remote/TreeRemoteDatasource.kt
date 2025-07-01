package studio.lunabee.microgallery.android.repository.datasource.remote

import studio.lunabee.microgallery.android.domain.Node

interface TreeRemoteDatasource {
    suspend fun fetchRoot(): Node
}
