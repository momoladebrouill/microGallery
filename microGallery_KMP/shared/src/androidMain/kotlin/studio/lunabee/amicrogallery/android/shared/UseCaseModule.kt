package studio.lunabee.amicrogallery.android.shared

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import studio.lunabee.microgallery.android.domain.settings.usecase.ObserveSettingsUseCase

val useCaseModule = module {
    factoryOf(::ObserveSettingsUseCase)
}