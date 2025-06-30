@file:Suppress("MatchingDeclarationName", "Filename")

package studio.lunabee.amicrogallery.android.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.AndroidSQLiteDriver

actual class RoomPlatformBuilder(private val context: Context) {
    actual fun getDriver(): SQLiteDriver = AndroidSQLiteDriver()
    actual fun builder(): RoomDatabase.Builder<RoomAppDatabase> {
        return Room.databaseBuilder<RoomAppDatabase>(context = context, name = context.getDatabasePath(DatabaseName).absolutePath)
    }
}
