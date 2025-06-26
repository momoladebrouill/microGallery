package studio.lunabee.amicrogallery.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import studio.lunabee.amicrogallery.calendar.CalendarPresenter
import studio.lunabee.amicrogallery.lastmonth.LastMonthPresenter
import studio.lunabee.amicrogallery.loading.LoadingPresenter
import studio.lunabee.amicrogallery.untimed.UntimedPresenter
import studio.lunabee.microgallery.android.domain.loading.LoadingRepository
import studio.lunabee.microgallery.android.domain.loading.usecase.UpdateTreeUseCase

val presentersModule = module {
    viewModelOf(::CalendarPresenter)
    viewModelOf(::LastMonthPresenter)
    viewModelOf(::UntimedPresenter)
    viewModelOf(::LoadingPresenter)
}


