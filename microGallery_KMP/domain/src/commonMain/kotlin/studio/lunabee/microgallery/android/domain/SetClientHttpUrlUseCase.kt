package studio.lunabee.microgallery.android.domain

import studio.lunabee.microgallery.android.data.SettingsData
import studio.lunabee.microgallery.android.domain.loading.LoadingRepository

class SetClientHttpUrlUseCase(
    val loadingRepository: LoadingRepository,
) {
    operator fun invoke(settingsData: SettingsData) {
        if (settingsData.useIpv6) {
            loadingRepository.setBaseRemoteUrl("[${settingsData.ipv6}]")
        } else {
            loadingRepository.setBaseRemoteUrl(settingsData.ipv4)
        }
    }
}
