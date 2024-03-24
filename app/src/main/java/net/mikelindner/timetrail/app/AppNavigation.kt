package net.mikelindner.timetrail.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.mikelindner.timetrail.domain.TrailsRepository
import net.mikelindner.timetrail.view.GeoView
import net.mikelindner.timetrail.view.GeoViewModel
import net.mikelindner.timetrail.view.TrailListView
import net.mikelindner.timetrail.view.TrailListViewModel
import net.mikelindner.timetrail.view.makeBottomBar

@Composable
fun AppNavigation(trailsRepo: TrailsRepository, appOptions: AppOptions) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreen.Map.route) {

        // Map
        composable(AppScreen.Map.route) {
            GeoView(
                vm = GeoViewModel(trailsRepo),
                appOptions = appOptions,
                bottomBar = makeBottomBar(AppScreen.Map, navController)
            )
        }

        // Trail list
        composable(AppScreen.TrailList.route)
        {
            TrailListView(
                vm = TrailListViewModel(trailsRepo),
                navBack = { navController.navigateUp() },
                bottomBar = makeBottomBar(AppScreen.TrailList, navController)
            )
        }
    }
}