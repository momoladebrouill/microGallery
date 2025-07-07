package studio.lunabee.microgallery.android.domain.loading.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import studio.lunabee.microgallery.android.data.Directory
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.domain.loading.LoadingRepository

class UpdateTreeUseCase( // update the tree from remote
    val loadingRepository: LoadingRepository,
) {
    operator fun invoke() : Flow<Unit> {
        return loadingRepository.getRootDir().map { rootDir ->
            loadingRepository.pictureDbFreshStart()
            for (year in rootDir.content) {
                val yearDir: Directory = year as Directory
                if (year.name == "untimed") {
                    loadingRepository.savePicturesInDb(yearDir.content.map { it as Picture })
                } else {
                    for (month in yearDir.content) {
                        val monthDir = month as Directory
                        loadingRepository.savePicturesInDb(monthDir.content.map { it as Picture })
                    }
                }
            }
        }
    }
}
