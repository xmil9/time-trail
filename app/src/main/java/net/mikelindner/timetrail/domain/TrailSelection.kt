package net.mikelindner.timetrail.domain

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap

class TrailSelection {

    private var selectedTrails: SnapshotStateMap<Trail, Boolean> = mutableStateMapOf()

    fun isSelected(trail: Trail): Boolean {
        return selectedTrails[trail] ?: false
    }

    fun select(trail: Trail, select: Boolean) {
        selectedTrails[trail] = select
    }

    fun filterSelected(trails: List<Trail>): List<Trail> {
        return trails.filter { this.isSelected(it) }
    }
}