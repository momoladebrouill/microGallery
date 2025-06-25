package studio.lunabee.amicrogallery.android.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.SQLiteDriver
import androidx.sqlite.execSQL
import studio.lunabee.amicrogallery.android.local.dao.PictureDao
import studio.lunabee.amicrogallery.android.local.entity.FolderEntity
import studio.lunabee.amicrogallery.android.local.entity.PictureEntity
import kotlin.coroutines.CoroutineContext


@Database(
    entities = [
        PictureEntity::class,
    ],
    version = 1,
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
        /*.addCallback(
            object : RoomDatabase.Callback() {
                override fun onCreate(connection: SQLiteConnection) {
                    super.onCreate(connection)
                    connection.execSQL("INSERT INTO $AppVisitTable (id) VALUES ('${AppVisitEntity.UniqueId}')")
                    connection.execSQL(
                        "INSERT INTO $VariantTable (id, environment) " +
                            "VALUES ('${VariantEntity.UniqueId}', '${defaultEnvironment.rawValue}')",
                    )
                }
            },
        )*/
        // TODO here goes future migrations.
        .setDriver(builder.getDriver())
        .setQueryCoroutineContext(context = context)
        .build()
}
