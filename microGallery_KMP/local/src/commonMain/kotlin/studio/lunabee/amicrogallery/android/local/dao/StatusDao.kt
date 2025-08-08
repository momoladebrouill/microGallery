package studio.lunabee.amicrogallery.android.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import studio.lunabee.amicrogallery.android.local.entity.StatusEntity
import studio.lunabee.amicrogallery.android.local.entity.StatusTable

@Dao
interface StatusDao {
    @Query("SELECT * FROM $StatusTable")
    fun getStatus(): Flow<StatusEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun store(statusEntity: StatusEntity)

    @Query("DELETE FROM $StatusTable")
    suspend fun dropStatus()
}
