package studio.lunabee.microgallery.android.domain.loading.usecase

import com.lunabee.lbcore.model.LBResult
import studio.lunabee.amicrogallery.android.error.CoreError
import studio.lunabee.microgallery.android.data.Directory
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.domain.loading.LoadingRepository

class UpdateTreeUseCase( // update the tree from remote
    val loadingRepository: LoadingRepository,
) {
    suspend operator fun invoke(): LBResult<Unit> = CoreError.Companion.runCatching {


        val rootDir = loadingRepository.getRootDir()
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
        return LBResult.Success(Unit)
    }
}
