package studio.lunabee.amicrogallery.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import studio.lunabee.amicrogallery.calendar.CalendarPresenter
import studio.lunabee.amicrogallery.lastmonth.LastMonthPresenter
import studio.lunabee.amicrogallery.untimed.UntimedPresenter

val presentersModule = module {
    viewModelOf(::CalendarPresenter)
    viewModelOf(::LastMonthPresenter)
    viewModelOf(::UntimedPresenter)
}
