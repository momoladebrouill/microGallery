package studio.lunabee.amicrogallery.android.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.SQLiteDriver
import androidx.sqlite.execSQL
import studio.lunabee.amicrogallery.android.local.dao.PictureDao
import studio.lunabee.amicrogallery.android.local.dao.SettingsDao
import studio.lunabee.amicrogallery.android.local.dao.StatusDao
import studio.lunabee.amicrogallery.android.local.entity.PictureEntity
import studio.lunabee.amicrogallery.android.local.entity.SettingsEntity
import studio.lunabee.amicrogallery.android.local.entity.SettingsTable
import studio.lunabee.microgallery.android.data.SettingsData
import kotlin.coroutines.CoroutineContext

@Database(
    entities = [
        PictureEntity::class,
        SettingsEntity::class,
        StatusEntity::class,
    ],
    version = 7,
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class RoomAppDatabase : RoomDatabase() {
    abstract fun pictureDao(): PictureDao
    abstract fun settingsDao(): SettingsDao
    abstract fun statusDao(): StatusDao
}

expect class RoomPlatformBuilder {
    fun builder(): RoomDatabase.Builder<RoomAppDatabase>
    fun getDriver(): SQLiteDriver
}

/**
 * Automatically generates actual implementation.
 */
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<RoomAppDatabase> {
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
                    val settingsEntity = SettingsEntity.fromSettingsData(SettingsData())
                    // this is executed only when the app is first launched after install
                    connection.execSQL(
                        "INSERT INTO $SettingsTable (ipvfour, ipvsix, useIpvSix, viewInHD)" +
                            "VALUES ('${settingsEntity.ipv4}', '${settingsEntity.ipv6}',"
                            + "'${settingsEntity.useIpv6}', ${settingsEntity.viewInHD})",
                    )
                }
            },
        )
        .fallbackToDestructiveMigration(true)
        .setDriver(builder.getDriver())
        .setQueryCoroutineContext(context = context)
        .build()
}
