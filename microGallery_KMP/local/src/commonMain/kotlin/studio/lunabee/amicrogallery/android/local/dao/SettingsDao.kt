package studio.lunabee.amicrogallery.android.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import studio.lunabee.amicrogallery.android.local.entity.SettingsEntity
import studio.lunabee.amicrogallery.android.local.entity.SettingsTable

@Dao
interface SettingsDao {
    @Query("SELECT * FROM $SettingsTable")
    fun getSettings(): Flow<SettingsEntity?> // flow will emit at each store called

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun storeSettings(settingsEntity: SettingsEntity)

    @Query("DELETE FROM $SettingsTable")
    suspend fun dropSettings()
}
