package net.mikelindner.timetrail.view.map

import android.graphics.drawable.Drawable
import net.mikelindner.timetrail.domain.Hsl
import net.mikelindner.timetrail.domain.Trail
import net.mikelindner.timetrail.domain.generateHues

interface Mapifier {
    fun addTrail(trail: Trail, color: Hsl, dotScale: Double, eventMarker: Drawable)
    fun clearTrails()

    companion object {
        fun eventDotSizeInMeters(durationInDays: Double): Double {
            val minSize = 20.0
            return minSize + if (durationInDays >= 0.0) durationInDays else 0.0
        }

        fun eventDotColors(baseColor: Hsl, numColors: Int): List<Hsl> {
            return generateHues(baseColor, numColors)
        }

        fun eventMarkerColor(baseColor: Hsl): Hsl {
            return Hsl(baseColor.hue, baseColor.saturation, 0.3f)
        }
    }
}
