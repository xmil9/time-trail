package net.mikelindner.timetrail.view

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.mikelindner.timetrail.domain.Trail
import net.mikelindner.timetrail.domain.TrailsRepository

class TrailListViewModel(
    private val trailsRepo: TrailsRepository
) : ViewModel() {

    var trails = mutableStateOf(listOf<Trail>())
        get
        private set

    private var selectedTrails: SnapshotStateMap<Trail, Boolean> = mutableStateMapOf()

    init {
        allTrails()
    }

    fun allTrails() {
        viewModelScope.launch {
            trails.value = trailsRepo.getTrailEventsAnyPlaceAnyTime()
        }
    }

    fun isSelected(trail: Trail): Boolean {
        return selectedTrails[trail] ?: false
    }

    fun select(trail: Trail, select: Boolean) {
        selectedTrails[trail] = select
    }
}