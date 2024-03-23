package net.mikelindner.timetrail.view.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.preference.PreferenceManager
import net.mikelindner.timetrail.R
import net.mikelindner.timetrail.app.AppOptions
import net.mikelindner.timetrail.domain.generateColors
import net.mikelindner.timetrail.view.GeoViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory

// Composable view wrapper for the XML based OSM MapView.
@Composable
fun OsmMapView(
    modifier: Modifier,
    vm: GeoViewModel,
    appOptions: AppOptions
) {
    val context = LocalContext.current

    AndroidView(
        modifier = modifier,
        factory = { context ->
            TrailsMapView(context).apply {
                // Load/initialize the osmdroid configuration.
                // This won't work unless you have imported this: org.osmdroid.config.Configuration.*.
                // It 'should' ensure that the map has a writable location for the map cache,
                // even without permissions.
                // If no tiles are displayed, you can try overriding the cache path using
                // Configuration.getInstance().setCachePath
                // See also StorageUtils,
                // Note, the load method also sets the HTTP User Agent to your application's package
                // name, if you abuse OSM's tile servers will get you banned based on this string.
                Configuration.getInstance()
                    .load(context, PreferenceManager.getDefaultSharedPreferences(context))

                setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
                overlayManager = TrailsOverlayManager(overlayManager, overlayManager.tilesOverlay)

                // Needed to keep map view from drawing outside of its bounds.
                clipToOutline = true
            }
        },
        update = { mapView ->
            val colors = generateColors(vm.trails.value.size)
            var colorIdx = 0

            val mapify = OsmMapifier(mapView)
            mapify.clearTrails()

            for (trail in vm.trails.value) {
                mapify.addTrail(
                    trail,
                    colors[colorIdx++],
                    10.0,
                    context.getDrawable(R.drawable.event_pin_20)!!
                )
            }
            // Make sure all markers are on top of event dots to make them clickable.
            if (appOptions.showEventMarkers)
                mapify.applyMarkerOverlays()

            if (vm.trails.value.isNotEmpty() && vm.trails.value[0].events.isNotEmpty())
                mapView.controller.setCenter(vm.trails.value[0].events.get(0).location)
            mapView.controller.setZoom(5.0)
        }
    )
}
