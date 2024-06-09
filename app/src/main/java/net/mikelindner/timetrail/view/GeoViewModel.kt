package net.mikelindner.timetrail.view

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.mikelindner.timetrail.domain.Date
import net.mikelindner.timetrail.domain.Trail
import net.mikelindner.timetrail.domain.TrailState

class GeoViewModel(
    private val trailState: TrailState
) : ViewModel() {

    var trails = mutableStateOf(listOf<Trail>())

    init {
        anyTime()
    }

    private val trailRepo
        get() = trailState.repo

    private fun filterSelected(trails: List<Trail>): List<Trail> {
        return trailState.selection.filterSelected(trails)
    }

    fun anyTime() {
        viewModelScope.launch {
            trails.value = filterSelected(trailRepo.getTrailEventsAnyPlaceAnyTime())
        }
    }

    fun filterTime(from: Date?, to: Date?) {
        if (from == null && to == null) {
            anyTime()
        } else if (from != null && to == null) {
            viewModelScope.launch {
                trails.value = filterSelected(trailRepo.getTrailEventsAnyPlaceStartedAfter(from))
            }
        } else if (from == null && to != null) {
            viewModelScope.launch {
                trails.value = filterSelected(trailRepo.getTrailEventsAnyPlaceStartedBefore(to))
            }
        } else if (from != null && to != null) {
            viewModelScope.launch {
                trails.value =
                    filterSelected(trailRepo.getTrailEventsAnyPlaceStartedDuring(from, to))
            }
        }
    }
}