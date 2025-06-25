package studio.lunabee.amicrogallery.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import studio.lunabee.amicrogallery.calendar.CalendarPresenter
import studio.lunabee.amicrogallery.lastmonth.LastMonthPresenter
import studio.lunabee.amicrogallery.loading.LoadingPresenter
import studio.lunabee.amicrogallery.loading.LoadingScreen
import studio.lunabee.amicrogallery.untimed.UntimedPresenter
import studio.lunabee.microgallery.android.domain.calendar.usecase.UpdateTreeUseCase

val presentersModule = module {
    viewModelOf(::CalendarPresenter)
    viewModelOf(::LastMonthPresenter)
    viewModelOf(::UntimedPresenter)
    viewModelOf(::LoadingPresenter)
}

val loadingModule = module {
    single { UpdateTreeUseCase(get()) }
}
