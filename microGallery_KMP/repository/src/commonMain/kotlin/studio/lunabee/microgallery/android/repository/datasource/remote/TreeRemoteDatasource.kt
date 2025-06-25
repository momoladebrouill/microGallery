package studio.lunabee.microgallery.android.repository.datasource.remote

import studio.lunabee.microgallery.android.data.Node

interface TreeRemoteDatasource {
    suspend fun fetchRoot()
    fun getRoot() : Node
}
