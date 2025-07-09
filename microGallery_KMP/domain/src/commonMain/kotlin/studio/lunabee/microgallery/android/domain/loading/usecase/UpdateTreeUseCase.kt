package studio.lunabee.microgallery.android.domain.loading.usecase

import kotlinx.coroutines.flow.first
import studio.lunabee.microgallery.android.data.Directory
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.domain.loading.LoadingRepository

class UpdateTreeUseCase(
    // update the tree from remote
    val loadingRepository: LoadingRepository,
) {
    suspend operator fun invoke() {
        val rootDir = loadingRepository.getRootDir().first()
        loadingRepository.pictureDbFreshStart()
        rootDir.content.filterIsInstance<Directory>().forEach { yearDir ->
            if (yearDir.name == "untimed") {
                loadingRepository.savePicturesInDb(yearDir.content.map { it as Picture })
            } else {
                yearDir.content.filterIsInstance<Directory>().forEach { monthDir ->
                    loadingRepository.savePicturesInDb(monthDir.content.map { it as Picture })
                }
            }
        }
    }
}
