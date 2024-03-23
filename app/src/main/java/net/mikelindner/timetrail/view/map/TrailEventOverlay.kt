package net.mikelindner.timetrail.view.map

import net.mikelindner.timetrail.domain.Event
import net.mikelindner.timetrail.domain.Hsl
import net.mikelindner.timetrail.domain.Trail
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polygon

class TrailEventOverlay(
    private val trail: Trail,
    private val trailColor: Hsl,
    private val trailEvent: Event,
    private val eventColor: Hsl,
    private val scale: Double,
    map: TrailsMapView
) : Polygon(map) {

    init {
        id = trailEvent.id.toString()
        title = trail.name
        snippet = trailEvent.description
        points =
            Polygon.pointsAsCircle(
                trailEvent.location,
                Mapifier.eventDotSizeInMeters(trailEvent.durationInDays()) * scale
            )
        fillPaint.color = Hsl.toArgb(eventColor, 0.5f)
        outlinePaint.strokeWidth = 4f
        outlinePaint.color = Hsl.toArgb(trailColor)
    }

    override fun click(pMapView: MapView?, pEventPos: GeoPoint?): Boolean {
        pMapView.apply {
            if (pMapView is TrailsMapView)
                pMapView.addClickedTrailEvent(trailEvent)
        }
        return super.click(pMapView, pEventPos)
    }
}