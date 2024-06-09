package net.mikelindner.timetrail.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import net.mikelindner.timetrail.domain.Date
import net.mikelindner.timetrail.domain.Event
import net.mikelindner.timetrail.domain.Trail
import net.mikelindner.timetrail.domain.TrailsRepository

class DbTrailRepository(private val trailDao: TrailDao) : TrailsRepository {

    override suspend fun getTrailsAnyPlaceAnyTime(): List<Trail> {
        // Wait for DB result to come in.
        val queryResult =
            GlobalScope.async(Dispatchers.Default) {
                trailDao.getTrailsAnyPlaceAnyTime()
            }.await()

        return queryResult
    }

    override suspend fun getTrailEventsAnyPlaceAnyTime(trails: List<Trail>?): List<Trail> {
        // Wait for DB result to come in.
        val queryResult =
            GlobalScope.async(Dispatchers.Default) {
                if (trails == null)
                    trailDao.getEventsAnyPlaceAnyTime()
                else
                    trailDao.getEventsAnyPlaceAnyTime(trails.map { it.id })
            }.await()

        return populateTrails(queryResult)
    }

    override suspend fun getTrailEventsAnyPlaceStartedAfterEndedBefore(
        from: Date,
        to: Date
    ): List<Trail> {
        // Wait for DB result to come in.
        val queryResult =
            GlobalScope.async(Dispatchers.Default) {
                trailDao.getEventsAnyPlaceStartedAfterEndedBefore(
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
                trailDao.getEventsAnyPlaceStartedDuring(
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
                trailDao.getEventsAnyPlaceStartedAfter(
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
                trailDao.getEventsAnyPlaceStartedBefore(
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