package net.mikelindner.timetrail.domain

class TrailState(
    val repo: TrailsRepository
) {
    val selection = TrailSelection()
}