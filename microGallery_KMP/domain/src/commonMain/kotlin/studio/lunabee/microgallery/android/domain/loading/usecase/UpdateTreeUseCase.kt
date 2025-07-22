package studio.lunabee.microgallery.android.domain.loading.usecase

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.Directory
import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.domain.loading.LoadingRepository

class UpdateTreeUseCase(
    // update the tree from remote
    val loadingRepository: LoadingRepository,
) {
    suspend operator fun invoke() {
        loadingRepository.pictureDbFreshStart()
        val years: List<MYear> = loadingRepository.getYears()
        val yearsFlow: Flow<Directory> = loadingRepository.getYearsAsFlow(years)
        yearsFlow.collect { yearDir ->
            if (yearDir.name.substringAfterLast('/') == "untimed") {
                loadingRepository.savePicturesInDb(yearDir.content.filterIsInstance<Picture>())
            } else {
                yearDir.content.filterIsInstance<Directory>().forEach { monthDir ->
                    loadingRepository.savePicturesInDb(monthDir.content.filterIsInstance<Picture>())
                }
            }
        }
    }
}
