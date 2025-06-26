package studio.lunabee.amicrogallery.android.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.SQLiteDriver
import androidx.sqlite.execSQL
import studio.lunabee.amicrogallery.android.local.dao.PictureDao
import studio.lunabee.amicrogallery.android.local.entity.PictureEntity
import kotlin.coroutines.CoroutineContext

@Database(
    entities = [
        PictureEntity::class,
    ],
    version = 2,
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class RoomAppDatabase : RoomDatabase() {
    abstract fun pictureDao() : PictureDao
}

expect class RoomPlatformBuilder {
    fun builder(): RoomDatabase.Builder<RoomAppDatabase>
    fun getDriver(): SQLiteDriver
}

/**
 * Automatically generates actual implementation.
 */
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<RoomAppDatabase>{
    // Workaround to allow standalone module build, needed for Dokka
    // See https://youtrack.jetbrains.com/issue/KT-59739#how-to-fix-your-code
    override fun initialize(): RoomAppDatabase
}

internal const val DatabaseName: String = "studio.lunabee.amicrogallery.android.db"

fun buildRoomDatabase(
    builder: RoomPlatformBuilder,
    context: CoroutineContext,
): RoomAppDatabase {
    return builder
        .builder()
        .addCallback(
            object : RoomDatabase.Callback() {
                override fun onCreate(connection: SQLiteConnection) {
                    super.onCreate(connection)
                    connection.execSQL("DELETE FROM PhotosTable")

                }
            },
        )
        // TODO here goes future migrations.
        .fallbackToDestructiveMigration(true)
        .setDriver(builder.getDriver())
        .setQueryCoroutineContext(context = context)
        .build()
}
