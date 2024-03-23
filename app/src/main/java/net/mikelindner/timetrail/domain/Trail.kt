package net.mikelindner.timetrail.domain

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import org.osmdroid.util.BoundingBox

@Entity(tableName = "trails")
class Trail(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @NonNull
    val name: String,
) {
    @Ignore
    var events = ArrayList<Event>()
        get() = field
        private set
    @Ignore
    var bounds = BoundingBox()
        get() = field
        private set

    override fun hashCode(): Int {
        return id.toInt()
    }

    override fun equals(other: Any?): Boolean {
        return other is Trail && id == other.id
    }

    fun addEvent(event: Event) {
        events.add(event)
        updateBounds(event)
    }

    fun sortEvents() {
        events.sortBy { it.start }
    }

    private fun updateBounds(event: Event) {
        bounds.bringToBoundingBox(
            event.location.latitude,
            event.location.longitude
        )
    }
}