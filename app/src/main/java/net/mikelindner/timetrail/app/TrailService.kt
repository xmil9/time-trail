package net.mikelindner.timetrail.app

import android.content.Context
import androidx.room.Room
import net.mikelindner.timetrail.db.TrailDb
import net.mikelindner.timetrail.db.DbTrailRepository
import net.mikelindner.timetrail.domain.TrailsRepository

object TrailService {
    lateinit var db: TrailDb

    val trailsRepo: TrailsRepository by lazy {
        DbTrailRepository(trailDao = db.trailsDao())
    }

    fun provide(context: Context) {
        db = Room.databaseBuilder(context, TrailDb::class.java, "trails.db")
            .createFromAsset("db/trails.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}