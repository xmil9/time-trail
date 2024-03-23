package net.mikelindner.timetrail.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import net.mikelindner.timetrail.view.GeoView
import net.mikelindner.timetrail.domain.Date
import net.mikelindner.timetrail.domain.Event
import net.mikelindner.timetrail.domain.Trail
import net.mikelindner.timetrail.ui.theme.TimeTrailTheme
import net.mikelindner.timetrail.view.GeoViewModel
import org.osmdroid.util.GeoPoint

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimeTrailTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GeoView(GeoViewModel(TrailsService.trailsRepo), AppOptions())
                }
            }
        }
    }
}
