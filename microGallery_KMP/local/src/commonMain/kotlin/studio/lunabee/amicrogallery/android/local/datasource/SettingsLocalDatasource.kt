package studio.lunabee.amicrogallery.android.local.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import studio.lunabee.amicrogallery.android.local.dao.SettingsDao
import studio.lunabee.amicrogallery.android.local.entity.SettingsEntity
import studio.lunabee.amicrogallery.settings.SettingsLocal
import studio.lunabee.microgallery.android.data.SettingsData
import studio.lunabee.microgallery.android.domain.loading.LoadingRepository

class SettingsLocalDatasource(
    private val settingsDao: SettingsDao,
) : SettingsLocal {

    override fun getSettings(): Flow<SettingsData> {
        return settingsDao.getSettings().map {settingsEntity : SettingsEntity -> settingsEntity.toSettingsData()}
    }

    override suspend fun storeSettings(settingsData: SettingsData) {
        settingsDao.storeSettings(SettingsEntity.fromSettingsData(settingsData))
    }

}
