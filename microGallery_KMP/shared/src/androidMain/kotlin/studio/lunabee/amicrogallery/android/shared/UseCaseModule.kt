package studio.lunabee.amicrogallery.android.shared

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import studio.lunabee.microgallery.android.domain.calendar.usecase.LoadPartialTreeUseCase
import studio.lunabee.microgallery.android.domain.calendar.usecase.ObserveYearPreviewsUseCase
import studio.lunabee.microgallery.android.domain.lastMonth.usecase.ObserveLastMonthUseCase
import studio.lunabee.microgallery.android.domain.loading.usecase.UpdateTreeUseCase
import studio.lunabee.microgallery.android.domain.photoviewer.usecase.GetNeighborsByPictureUseCase
import studio.lunabee.microgallery.android.domain.photoviewer.usecase.ObservePictureByIdUseCase
import studio.lunabee.microgallery.android.domain.settings.usecase.ObserveSettingsUseCase
import studio.lunabee.microgallery.android.domain.settings.usecase.ObserveStatusUseCase
import studio.lunabee.microgallery.android.domain.untimed.usecase.ObserveUntimedUseCase

val useCaseModule = module {
    factoryOf(::ObserveSettingsUseCase)
    factoryOf(::ObserveUntimedUseCase)
    factoryOf(::ObserveLastMonthUseCase)
    factoryOf(::ObservePictureByIdUseCase)
    factoryOf(::ObserveYearPreviewsUseCase)
    factoryOf(::LoadPartialTreeUseCase)
    factoryOf(::UpdateTreeUseCase)
    factoryOf(::ObserveStatusUseCase)
    factoryOf(::GetNeighborsByPictureUseCase)
}
