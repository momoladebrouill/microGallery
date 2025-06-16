@file:Suppress("MatchingDeclarationName", "Filename")

package template.local

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.NativeSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

actual class RoomPlatformBuilder {
    actual fun getDriver(): SQLiteDriver = NativeSQLiteDriver()
    actual fun builder(): RoomDatabase.Builder<RoomAppDatabase> {
        val dbFilePath = "${fileDirectory()}/$DatabaseName"
        return Room.databaseBuilder<RoomAppDatabase>(name = dbFilePath)
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun fileDirectory(): String {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        return requireNotNull(documentDirectory?.path)
    }
}
