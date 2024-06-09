package net.mikelindner.timetrail.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import net.mikelindner.timetrail.domain.TrailState
import net.mikelindner.timetrail.ui.theme.TimeTrailTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimeTrailTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val trailState = TrailState(TrailsService.trailsRepo)
                    AppNavigation(trailState, AppOptions())
                }
            }
        }
    }
}
