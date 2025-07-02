package studio.lunabee.amicrogallery.android.local.datasource

import studio.lunabee.amicrogallery.android.local.dao.SettingsDao
import studio.lunabee.amicrogallery.android.local.entity.SettingsEntity
import studio.lunabee.amicrogallery.settings.SettingsLocal
import studio.lunabee.microgallery.android.data.SettingsData

class SettingsLocalDatasource(
    private val settingsDao : SettingsDao
) : SettingsLocal {
    override suspend fun getSettings() : SettingsData{
        return settingsDao.getSettings().toSettingsData()
    }
    override suspend fun storeSettings(settingsData: SettingsData){
        settingsDao.storeSettings(SettingsEntity.fromSettingsData(settingsData))
    }
}