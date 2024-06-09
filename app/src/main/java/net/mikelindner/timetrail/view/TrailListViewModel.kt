package net.mikelindner.timetrail.view

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.mikelindner.timetrail.domain.Trail
import net.mikelindner.timetrail.domain.TrailState

class TrailListViewModel(
    private val trailState: TrailState
) : ViewModel() {

    var trails = mutableStateOf(listOf<Trail>())
        get
        private set

    init {
        allTrails()
    }

    fun allTrails() {
        viewModelScope.launch {
            trails.value = trailState.repo.getTrailEventsAnyPlaceAnyTime()
        }
    }

    fun isSelected(trail: Trail): Boolean {
        return trailState.selection.isSelected(trail)
    }

    fun select(trail: Trail, select: Boolean) {
        trailState.selection.select(trail, select)
    }
}