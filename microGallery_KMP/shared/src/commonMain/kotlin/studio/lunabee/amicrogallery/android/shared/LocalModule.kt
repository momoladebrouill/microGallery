package studio.lunabee.amicrogallery.android.shared

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import studio.lunabee.amicrogallery.android.local.RoomAppDatabase
import studio.lunabee.amicrogallery.android.local.buildRoomDatabase
import studio.lunabee.amicrogallery.android.local.dao.PictureDao
import studio.lunabee.amicrogallery.android.local.dao.SettingsDao
import studio.lunabee.amicrogallery.android.local.dao.StatusDao
import studio.lunabee.amicrogallery.android.local.datasource.PictureLocalDatasource
import studio.lunabee.amicrogallery.android.local.datasource.SettingsLocalDatasource
import studio.lunabee.amicrogallery.android.local.datasource.StatusLocalDatasource
import studio.lunabee.microgallery.android.repository.datasource.local.PictureLocal
import studio.lunabee.microgallery.android.repository.datasource.local.SettingsLocal
import studio.lunabee.microgallery.android.repository.datasource.local.StatusLocal

internal val LocalModule: Module = module {

    // Database
    single<RoomAppDatabase> {
        buildRoomDatabase(
            builder = get(),
            context = Dispatchers.IO,
        )
    }

    // Bind repository expectations
    singleOf(::PictureLocalDatasource) bind PictureLocal::class
    singleOf(::SettingsLocalDatasource) bind SettingsLocal::class
    singleOf(::StatusLocalDatasource) bind StatusLocal::class

    // Dao
    single<PictureDao> { get<RoomAppDatabase>().pictureDao() }
    single<SettingsDao> { get<RoomAppDatabase>().settingsDao() }
    single<StatusDao> { get<RoomAppDatabase>().statusDao() }
}
