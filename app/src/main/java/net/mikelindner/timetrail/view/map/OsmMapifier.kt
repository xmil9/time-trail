package net.mikelindner.timetrail.view.map

import android.graphics.drawable.Drawable
import net.mikelindner.timetrail.domain.Hsl
import net.mikelindner.timetrail.domain.Trail
import org.osmdroid.views.overlay.Marker

class OsmMapifier(val map: TrailsMapView) : Mapifier {
    private val markerOverlays = ArrayList<Marker>()

    override fun addTrail(trail: Trail, color: Hsl, dotScale: Double, eventMarker: Drawable) {
        val rgb = Hsl.toArgb(color)

        // Set up trail line.
        val trailLine = TrailLineOverlay(trail, color, map)
        map.overlays.add(trailLine)

        // Set up event dots and markers.
        val markerColor = Mapifier.eventMarkerColor(color)
        eventMarker.setTint(Hsl.toArgb(markerColor))
        val dotColors = Mapifier.eventDotColors(color, trail.events.size)

        trail.events.forEachIndexed { index, event ->
            val dot = TrailEventOverlay(trail, color, event, dotColors[index], dotScale, map)
            map.overlays.add(dot)

            // Create markers but only apply them later to make sure they are on top of
            // all dots.
            val marker = TrailEventMarker(trail, color, event, dotColors[index], eventMarker, map)
            markerOverlays.add(marker)
        }
    }

    override fun clearTrails() {
        map.overlays.clear()
    }

    fun applyMarkerOverlays() {
        val topMost = map.overlays.size
        map.overlays.addAll(topMost, markerOverlays)
    }
}