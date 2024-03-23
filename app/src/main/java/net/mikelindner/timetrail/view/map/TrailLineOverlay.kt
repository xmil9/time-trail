package net.mikelindner.timetrail.view.map

import net.mikelindner.timetrail.domain.Hsl
import net.mikelindner.timetrail.domain.Trail
import org.osmdroid.views.overlay.Polyline

class TrailLineOverlay(private val trail: Trail, private val color: Hsl, map: TrailsMapView) :
    Polyline(map) {

    init {
        id = trail.id.toString()
        title = trail.name
        for (event in trail.events) {
            addPoint(event.location)
        }
        outlinePaint.strokeWidth = 5f
        outlinePaint.color = Hsl.toArgb(color)
    }
}
