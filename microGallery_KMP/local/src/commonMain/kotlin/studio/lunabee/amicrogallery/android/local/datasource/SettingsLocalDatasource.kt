package studio.lunabee.amicrogallery.android.local.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import studio.lunabee.amicrogallery.android.local.dao.SettingsDao
import studio.lunabee.amicrogallery.android.local.entity.SettingsEntity
import studio.lunabee.microgallery.android.data.SettingsData
import studio.lunabee.microgallery.android.repository.datasource.local.SettingsLocal

class SettingsLocalDatasource(
    private val settingsDao: SettingsDao,
) : SettingsLocal {

    override fun getSettings(): Flow<SettingsData> {
        return settingsDao.getSettings().map {
            if (it == null) {
                storeSettings(SettingsData())
                SettingsData()
            } else {
                it.toSettingsData()
            }
        }
    }

    override suspend fun storeSettings(settingsData: SettingsData) {
        settingsDao.dropSettings()
        settingsDao.storeSettings(SettingsEntity.fromSettingsData(settingsData))
    }
}
