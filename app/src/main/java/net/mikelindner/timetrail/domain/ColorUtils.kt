package net.mikelindner.timetrail.domain

import androidx.compose.ui.graphics.Color

// Darken color by adding black.
// Factor [0..1], smaller => darker.
fun shade(c: Color, factor: Float): Color {
    return Color(
        c.red * factor,
        c.green * factor,
        c.blue * factor,
        c.alpha
    )
}

// Lighten color by adding black.
// Factor [0..1], larger => lighter.
fun tint(c: Color, factor: Float): Color {
    return Color(
        c.red + (1f - c.red) * factor,
        c.green + (1f - c.green) * factor,
        c.blue + (1f - c.blue) * factor,
        c.alpha
    )
}

fun generateHues(c: Color, numHues: Int): List<Color> {
    val hues = mutableListOf<Color>()
    if (numHues <= 0)
        return hues

    val skipExtremes = 2
    val inc = 1.0F / (numHues + 1 + 2 * skipExtremes).toFloat()
    var factor = (skipExtremes + 1) * inc

    val numLighter = numHues / 2
    for (i in 1..numHues) {
        hues.add(tint(c, factor))
        factor += inc
    }

    hues.reverse()
    return hues
}

fun generateHues(c: Hsl, numHues: Int): List<Hsl> {
    val hues = mutableListOf<Hsl>()
    if (numHues <= 0)
        return hues

    val darkest = .3f
    val lightest = .9f
    val inc = (lightest - darkest) / (numHues + 1)

    var lightness = lightest
    for (i in 1..numHues) {
        hues.add(Hsl(c.hue, c.saturation, lightness))
        lightness -= inc
    }

    return hues
}

fun generateColors(numColors: Int): List<Hsl> {
    val colors = mutableListOf<Hsl>()
    if (numColors <= 0)
        return colors

    val fromHue = 0f
    val toHue = 360f
    val inc = (toHue - fromHue) / (numColors + 1)

    var hue = fromHue
    for (i in 1..numColors) {
        colors.add(Hsl(hue, 1f, 0.5f))
        hue += inc
    }

    return colors
}