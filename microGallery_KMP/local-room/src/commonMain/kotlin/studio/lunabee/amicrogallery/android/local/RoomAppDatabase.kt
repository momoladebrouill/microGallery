package studio.lunabee.amicrogallery.android.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.SQLiteDriver
import kotlin.coroutines.CoroutineContext

@Entity
data class PlaceHolderEntity(
    @PrimaryKey
    val id: Long,
)

@Database(
    entities = [
        PlaceHolderEntity::class,
    ],
    version = 1,
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class RoomAppDatabase : RoomDatabase()

expect class RoomPlatformBuilder {
    fun builder(): RoomDatabase.Builder<RoomAppDatabase>
    fun getDriver(): SQLiteDriver
}

/**
 * Automatically generates actual implementation.
 */
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<RoomAppDatabase>

internal const val DatabaseName: String = "studio.lunabee.amicrogallery.android.db"

fun buildRoomDatabase(
    builder: RoomPlatformBuilder,
    context: CoroutineContext,
): RoomAppDatabase {
    error("Please remove ${PlaceHolderEntity::class.simpleName}")
    return builder
        .builder()
        // TODO here goes future migrations.
        .setDriver(builder.getDriver())
        .setQueryCoroutineContext(context = context)
        .build()
}
