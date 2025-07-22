package studio.lunabee.amicrogallery.android.shared

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import studio.lunabee.microgallery.android.domain.SettingsDataRepository
import studio.lunabee.microgallery.android.domain.calendar.CalendarRepository
import studio.lunabee.microgallery.android.domain.lastMonth.LastMonthRepository
import studio.lunabee.microgallery.android.domain.loading.LoadingRepository
import studio.lunabee.microgallery.android.domain.photoviewer.PhotoViewerRepository
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository
import studio.lunabee.microgallery.android.domain.untimed.UntimedRepository
import studio.lunabee.microgallery.android.repository.impl.CalendarRepositoryImpl
import studio.lunabee.microgallery.android.repository.impl.LastMonthRepositoryImpl
import studio.lunabee.microgallery.android.repository.impl.LoadingRepositoryImpl
import studio.lunabee.microgallery.android.repository.impl.PhotoViewerRepositoryImpl
import studio.lunabee.microgallery.android.repository.impl.SettingsDataRepositoryImpl
import studio.lunabee.microgallery.android.repository.impl.SettingsRepositoryImpl
import studio.lunabee.microgallery.android.repository.impl.UntimedRepositoryImpl

val repositoryModule = module {
    singleOf(::CalendarRepositoryImpl) bind CalendarRepository::class
    singleOf(::LoadingRepositoryImpl) bind LoadingRepository::class
    singleOf(::UntimedRepositoryImpl) bind UntimedRepository::class
    singleOf(::LastMonthRepositoryImpl) bind LastMonthRepository::class
    factoryOf(::PhotoViewerRepositoryImpl) bind PhotoViewerRepository::class
    singleOf(::SettingsRepositoryImpl) bind SettingsRepository::class
    singleOf(::SettingsDataRepositoryImpl) bind SettingsDataRepository::class
}
