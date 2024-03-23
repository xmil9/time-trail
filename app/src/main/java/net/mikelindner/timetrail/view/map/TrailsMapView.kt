package net.mikelindner.timetrail.view.map

import android.content.Context
import android.view.MotionEvent
import net.mikelindner.timetrail.domain.Event
import org.osmdroid.views.MapView

// Subclass to customize OSM MapView's functionality.
class TrailsMapView(context: Context) : MapView(context) {

    private var clickedEvents = ArrayList<Event>()

    fun addClickedTrailEvent(trailEvent: Event) {
        clickedEvents.add(trailEvent)
    }

    fun clearClickedTrailEvents() {
        clickedEvents.clear()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev);
    }
}
