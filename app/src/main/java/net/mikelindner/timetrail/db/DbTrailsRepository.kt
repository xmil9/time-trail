package net.mikelindner.timetrail.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import net.mikelindner.timetrail.domain.Date
import net.mikelindner.timetrail.domain.Event
import net.mikelindner.timetrail.domain.Trail
import net.mikelindner.timetrail.domain.TrailsRepository

class DbTrailsRepository(private val trailsDao: TrailsDao) : TrailsRepository {
    override suspend fun getTrailEventsAnyPlaceAnyTime(): List<Trail> {
        // Wait for DB result to come in.
        val queryResult =
            GlobalScope.async(Dispatchers.Default) { trailsDao.getEventsAnyPlaceAnyTime() }.await()
        return populateTrails(queryResult)
    }

    override suspend fun getTrailEventsAnyPlaceStartedAfterEndedBefore(
        from: Date,
        to: Date
    ): List<Trail> {
        // Wait for DB result to come in.
        val queryResult =
            GlobalScope.async(Dispatchers.Default) {
                trailsDao.getEventsAnyPlaceStartedAfterEndedBefore(
                    Date.julianDays(from),
                    Date.julianDays(to)
                )
            }
                .await()

        return populateTrails(queryResult)
    }

    override suspend fun getTrailEventsAnyPlaceStartedDuring(
        from: Date,
        to: Date
    ): List<Trail> {
        // Wait for DB result to come in.
        val queryResult =
            GlobalScope.async(Dispatchers.Default) {
                trailsDao.getEventsAnyPlaceStartedDuring(
                    Date.julianDays(from),
                    Date.julianDays(to)
                )
            }
                .await()

        return populateTrails(queryResult)
    }

    override suspend fun getTrailEventsAnyPlaceStartedAfter(after: Date): List<Trail> {
        // Wait for DB result to come in.
        val queryResult =
            GlobalScope.async(Dispatchers.Default) {
                trailsDao.getEventsAnyPlaceStartedAfter(
                    Date.julianDays(after)
                )
            }
                .await()

        return populateTrails(queryResult)
    }

    override suspend fun getTrailEventsAnyPlaceStartedBefore(before: Date): List<Trail> {
        // Wait for DB result to come in.
        val queryResult =
            GlobalScope.async(Dispatchers.Default) {
                trailsDao.getEventsAnyPlaceStartedBefore(
                    Date.julianDays(before)
                )
            }
                .await()

        return populateTrails(queryResult)
    }

    private fun populateTrails(trailEvents: Map<Trail, List<Event>>): List<Trail> {
        val populatedTrails = ArrayList<Trail>()
        for (entry in trailEvents) {
            val trail = entry.key
            for (event in entry.value)
                trail.addEvent(event)
            populatedTrails.add(trail)
        }

        return populatedTrails
    }
}