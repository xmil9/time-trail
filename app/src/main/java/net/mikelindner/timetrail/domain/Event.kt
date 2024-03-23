package net.mikelindner.timetrail.domain

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import org.osmdroid.util.GeoPoint

@Entity(tableName = "events")
class Event(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @NonNull
    val label: String,
    @NonNull
    val lat: Double,
    @NonNull
    val lng: Double,
    @NonNull
    val start: Date,
    @NonNull
    val trailId: Long,
    val order: Double,
    val end: Date? = null,
) {
    val location: GeoPoint
        get() = GeoPoint(lat, lng)

    val description: String
        get() {
            var s = "$label<br>$start"
            if (end != null)
                s += " - $end"
            return s
        }

    override fun hashCode(): Int {
        return id.toInt()
    }

    override fun equals(other: Any?): Boolean {
        return other is Event && id == other.id
    }

    fun durationInDays(): Double {
        if (end == null)
            return 0.0

        val deltaYears = end.year - start.year
        val deltaMonths = (end.month ?: 0) - (start.month ?: 0)
        val deltaDays = (end.day ?: 0) - (start.day ?: 0)
        return deltaYears * 365.25 + deltaMonths * 30.437 + deltaDays
    }
}