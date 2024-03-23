package net.mikelindner.timetrail.domain

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

data class Hsl(
    val hue: Float,
    val saturation: Float = 1f,
    val lightness: Float = 0.5f,
) {

    init {
        require(hue in 0f..360f) {
            "HSL hue must be in [0..360]."
        }
        require(saturation in 0f..1f) {
            "HSL saturation must be in range [0..1]."
        }
        require(lightness in 0f..1f) {
            "HSL lightness must be in range [0..1]"
        }
    }

    companion object {
        fun toColor(hsl: Hsl, alpha: Float = 1f): Color {
            return Color.hsl(hsl.hue, hsl.saturation, hsl.lightness, alpha)
        }

        fun toArgb(hsl: Hsl, alpha: Float = 1f): Int {
            return Hsl.toColor(hsl, alpha).toArgb()
        }
    }
}