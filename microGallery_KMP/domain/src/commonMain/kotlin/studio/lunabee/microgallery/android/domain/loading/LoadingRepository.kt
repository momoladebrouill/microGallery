package studio.lunabee.microgallery.android.domain.loading

interface LoadingRepository {
    suspend fun fetchRootNode()
}
