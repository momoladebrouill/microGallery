package studio.lunabee.amicrogallery.android.shared

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import studio.lunabee.microgallery.android.domain.calendar.CalendarRepository
import studio.lunabee.microgallery.android.repository.impl.CalendarRepositoryImpl

val calendarRepoModule = module {
    singleOf(::CalendarRepositoryImpl) bind CalendarRepository::class
}
