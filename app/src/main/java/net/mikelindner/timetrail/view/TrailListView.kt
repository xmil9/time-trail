package net.mikelindner.timetrail.view

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import net.mikelindner.timetrail.app.AppScreen

@Composable
fun TrailListView(
    vm: TrailListViewModel,
    navBack: () -> Unit,
    bottomBar: @Composable () -> Unit
) {
    Scaffold(
        bottomBar = bottomBar,
        topBar = {
            TopBarView(
                title = AppScreen.TrailList.title,
                showBackButton = true
            ) { navBack() }
        },
    ) { padding ->

        padding
    }
}