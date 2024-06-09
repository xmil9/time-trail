package net.mikelindner.timetrail.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.mikelindner.timetrail.domain.Event
import net.mikelindner.timetrail.domain.Trail

@Database(
    entities = [Trail::class, Event::class],
    exportSchema = false,
    version = 3
)
@TypeConverters(DbConverters::class)
abstract class TrailDb : RoomDatabase() {
    abstract fun trailsDao(): TrailDao
}
