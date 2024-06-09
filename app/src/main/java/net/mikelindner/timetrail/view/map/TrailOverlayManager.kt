package net.mikelindner.timetrail.view.map

import android.view.MotionEvent
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.DefaultOverlayManager
import org.osmdroid.views.overlay.OverlayManager
import org.osmdroid.views.overlay.OverlayWithIW
import org.osmdroid.views.overlay.TilesOverlay

class TrailOverlayManager(
    private val originalManager: OverlayManager,
    tilesOverlay: TilesOverlay
) : DefaultOverlayManager(tilesOverlay) {

    // Only used to hold the displayed data for touches on multiple trail events.
    class MultiEventOverlay : OverlayWithIW() {
        fun populate(eventOverlays: ArrayList<TrailEventOverlay>) {
            when (eventOverlays.size) {
                0 -> populateNoEvent()
                1 -> populateSingleEvent(eventOverlays[0])
                else -> populateMultipleEvents(eventOverlays)
            }
        }

        private fun populateNoEvent() {
            title = "No event found."
        }

        private fun populateSingleEvent(eventOverlay: TrailEventOverlay) {
            title = eventOverlay.title
            snippet = eventOverlay.snippet
        }

        private fun populateMultipleEvents(eventOverlays: ArrayList<TrailEventOverlay>) {
            title = "Multiple Events"

            var info = "<br>"
            for (eventOverlay in eventOverlays.reversed()) {
                info += eventOverlay.title + "<br>"
                info += eventOverlay.snippet + "<br>"
                info += "<br>"
            }
            snippet = trimTrailingEmptyLines(info)
        }
    }

    private var touchedEventOverlays = ArrayList<TrailEventOverlay>()
    private var multiEventInfo = MultiEventOverlay()

    private fun collectTouchedTrailEvents(uiEvent: MotionEvent) {
        for (overlay in overlaysReversed()) {
            if ((overlay is TrailEventOverlay) && overlay.contains(uiEvent))
                touchedEventOverlays.add(overlay)
        }
    }

    private fun displayTouchedTrailEvents(map: MapView, touchX: Float, touchY: Float): Boolean {
        if (touchedEventOverlays.isNotEmpty()) {
            showMultiEventInfoWindow(map, getTouchedGeoPosition(map, touchX, touchY))
            touchedEventOverlays.clear()
            return true
        }
        return false
    }

    private fun clearTouchedTrailEvents() {
        touchedEventOverlays.clear()
    }

    private fun showMultiEventInfoWindow(map: MapView, pos: GeoPoint) {
        // Lazy init info window instance.
        if (multiEventInfo.infoWindow == null)
            multiEventInfo.infoWindow = map.getRepository().defaultPolygonInfoWindow

        multiEventInfo.populate(touchedEventOverlays)
        multiEventInfo.infoWindow.open(multiEventInfo, pos, 0, 0)
    }

    override fun onDown(event: MotionEvent?, map: MapView?): Boolean {
        if (event != null)
            collectTouchedTrailEvents(event)
        return super.onDown(event, map)
    }

    override fun onSingleTapConfirmed(event: MotionEvent?, map: MapView?): Boolean {
        if (event != null && map != null) {
            if (displayTouchedTrailEvents(map, event.x, event.y))
                return true
        }
        return super.onSingleTapConfirmed(event, map)
    }

    override fun onDoubleTap(event: MotionEvent?, map: MapView?): Boolean {
        clearTouchedTrailEvents()
        return super.onDoubleTap(event, map)
    }

    override fun onDoubleTapEvent(event: MotionEvent?, map: MapView?): Boolean {
        clearTouchedTrailEvents()
        return super.onDoubleTapEvent(event, map)
    }

    override fun onFling(
        event1: MotionEvent?, event2: MotionEvent?,
        velocityX: Float, velocityY: Float, map: MapView?
    ): Boolean {
        clearTouchedTrailEvents()
        return super.onFling(event1, event2, velocityX, velocityY, map)
    }

    override fun onLongPress(event: MotionEvent?, map: MapView?): Boolean {
        clearTouchedTrailEvents()
        return super.onLongPress(event, map)
    }

    override fun onScroll(
        event1: MotionEvent?, event2: MotionEvent?,
        distanceX: Float, distanceY: Float, map: MapView?
    ): Boolean {
        clearTouchedTrailEvents()
        return super.onScroll(event1, event2, distanceX, distanceY, map)
    }

    companion object {
        private fun getTouchedGeoPosition(map: MapView, touchX: Float, touchY: Float): GeoPoint {
            return map.projection.fromPixels(touchX.toInt(), touchY.toInt()) as GeoPoint
        }

        private fun trimTrailingEmptyLines(s: String): String {
            var trimmed = s
            while (trimmed.endsWith("<br>"))
                trimmed = trimmed.substringBeforeLast("<br>")
            return trimmed
        }
    }
}
