package net.mikelindner.timetrail.view

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.mikelindner.timetrail.domain.Trail
import net.mikelindner.timetrail.domain.TrailsRepository

class TrailListViewModel(
    private val trailsRepo: TrailsRepository
) : ViewModel() {

    var trails = mutableStateOf(listOf<Trail>())

    init {
        allTrails()
    }

    fun allTrails() {
        viewModelScope.launch {
            trails.value = trailsRepo.getTrailEventsAnyPlaceAnyTime()
        }
    }
}