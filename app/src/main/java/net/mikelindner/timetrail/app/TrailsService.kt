package net.mikelindner.timetrail.app

import android.content.Context
import androidx.room.Room
import net.mikelindner.timetrail.db.TrailsDb
import net.mikelindner.timetrail.db.DbTrailsRepository
import net.mikelindner.timetrail.domain.TrailsRepository

object TrailsService {
    lateinit var db: TrailsDb

    val trailsRepo: TrailsRepository by lazy {
        DbTrailsRepository(trailsDao = db.trailsDao())
    }

    fun provide(context: Context) {
        db = Room.databaseBuilder(context, TrailsDb::class.java, "trails.db")
            .createFromAsset("db/trails.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}