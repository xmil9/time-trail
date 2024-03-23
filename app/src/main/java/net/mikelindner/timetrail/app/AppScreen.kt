package net.mikelindner.timetrail.app

import androidx.annotation.DrawableRes
import net.mikelindner.timetrail.R

sealed class AppScreen(val title: String, val route: String, @DrawableRes val icon: Int) {
    data object Map: AppScreen("Map", "map", R.drawable.map_24)
    data object TrailList: AppScreen("Trails", "trail-list", R.drawable.trail_list_24)
}