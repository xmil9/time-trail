package net.mikelindner.timetrail.domain

interface TrailsRepository {
    suspend fun getTrailEventsAnyPlaceAnyTime(): List<Trail>
    suspend fun getTrailEventsAnyPlaceStartedAfterEndedBefore(from: Date, to: Date): List<Trail>
    suspend fun getTrailEventsAnyPlaceStartedDuring(from: Date, to: Date): List<Trail>
    suspend fun getTrailEventsAnyPlaceStartedAfter(after: Date): List<Trail>
    suspend fun getTrailEventsAnyPlaceStartedBefore(before: Date): List<Trail>
}

class NullTrailsRepository : TrailsRepository {
    override suspend fun getTrailEventsAnyPlaceAnyTime(): List<Trail> {
        return emptyList()
    }

    override suspend fun getTrailEventsAnyPlaceStartedAfterEndedBefore(
        from: Date,
        to: Date
    ): List<Trail> {
        return emptyList()
    }

    override suspend fun getTrailEventsAnyPlaceStartedDuring(
        from: Date,
        to: Date
    ): List<Trail> {
        return emptyList()
    }

    override suspend fun getTrailEventsAnyPlaceStartedAfter(after: Date): List<Trail> {
        return emptyList()
    }

    override suspend fun getTrailEventsAnyPlaceStartedBefore(before: Date): List<Trail> {
        return emptyList()
    }
}