package studio.lunabee.amicrogallery.android.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import studio.lunabee.amicrogallery.android.local.entity.SettingsEntity
import studio.lunabee.amicrogallery.android.local.entity.SettingsTable
import studio.lunabee.microgallery.android.data.SettingsData

@Dao
interface SettingsDao {
    @Query("SELECT * FROM $SettingsTable")
    suspend fun getSettings() : SettingsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun storeSettings(settingsEntity: SettingsEntity)

}