package studio.lunabee.amicrogallery.android.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import studio.lunabee.amicrogallery.android.local.entity.PhotosTable
import studio.lunabee.amicrogallery.android.local.entity.PictureEntity

@Dao
interface PictureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPicturesEntities(friends: List<PictureEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPicturesEntity(picture: PictureEntity)

    @Query("SELECT * FROM $PhotosTable")
    fun getPicturesEntities(): Flow<List<PictureEntity>>

    @Query("SELECT DISCTINCT(year) FROM $PhotosTable")
    fun getYears() : Flow<List<Int>>
}