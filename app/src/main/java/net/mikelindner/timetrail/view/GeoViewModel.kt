package net.mikelindner.timetrail.view

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.mikelindner.timetrail.domain.Date
import net.mikelindner.timetrail.domain.Trail
import net.mikelindner.timetrail.domain.TrailsRepository

class GeoViewModel(
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

    fun timeConstrainedTrails(from: Date?, to: Date?) {
        if (from == null && to == null) {
            allTrails()
        } else if (from != null && to == null) {
            viewModelScope.launch {
                trails.value = trailsRepo.getTrailEventsAnyPlaceStartedAfter(from)
            }
        } else if (from == null && to != null) {
            viewModelScope.launch {
                trails.value = trailsRepo.getTrailEventsAnyPlaceStartedBefore(to)
            }
        } else if (from != null && to != null){
            viewModelScope.launch {
                trails.value = trailsRepo.getTrailEventsAnyPlaceStartedDuring(from, to)
            }
        }
    }
}