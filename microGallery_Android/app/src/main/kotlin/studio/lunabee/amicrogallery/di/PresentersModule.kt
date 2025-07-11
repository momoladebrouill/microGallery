package studio.lunabee.amicrogallery.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import studio.lunabee.amicrogallery.calendar.CalendarPresenter
import studio.lunabee.amicrogallery.lastmonth.LastMonthPresenter
import studio.lunabee.amicrogallery.loading.LoadingPresenter
import studio.lunabee.amicrogallery.photoviewer.PhotoViewerPresenter
import studio.lunabee.amicrogallery.reorder.ReorderPresenter
import studio.lunabee.amicrogallery.settings.SettingsPresenter
import studio.lunabee.amicrogallery.untimed.UntimedPresenter

val presentersModule = module {
    viewModelOf(::CalendarPresenter)
    viewModelOf(::LastMonthPresenter)
    viewModelOf(::UntimedPresenter)
    viewModelOf(::SettingsPresenter)
    viewModelOf(::LoadingPresenter)
    viewModelOf(::PhotoViewerPresenter)
    viewModelOf(::ReorderPresenter)
}
