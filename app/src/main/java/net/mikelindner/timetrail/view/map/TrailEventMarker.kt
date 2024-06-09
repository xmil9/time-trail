package net.mikelindner.timetrail.view.map

import android.graphics.drawable.Drawable
import net.mikelindner.timetrail.domain.Event
import net.mikelindner.timetrail.domain.Hsl
import net.mikelindner.timetrail.domain.Trail
import org.osmdroid.views.overlay.Marker

class TrailEventMarker(
    private val trail: Trail,
    private val trailColor: Hsl,
    private val event: Event,
    private val eventColor: Hsl,
    eventMarker: Drawable,
    map: TrailMapView
) : Marker(map) {

    init {
        id = event.id.toString()
        icon = eventMarker
        position = event.location
        title = trail.name
        snippet = event.description
        setPanToView(false)
    }
}