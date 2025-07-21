package studio.lunabee.amicrogallery.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import studio.lunabee.amicrogallery.bottomBar.BottomBarManager
import studio.lunabee.amicrogallery.bottomBar.BottomBarViewModel

val uiHelpersModule = module {
    singleOf(::BottomBarManager)
    viewModelOf(::BottomBarViewModel)
}
