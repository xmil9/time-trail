package net.mikelindner.timetrail.domain

interface TrailsRepository {
    // Returns trails without event data.
    suspend fun getTrailsAnyPlaceAnyTime(): List<Trail>

    // Returns trails with event data filtered by:
    //  - optional trail id
    suspend fun getTrailEventsAnyPlaceAnyTime(trails: List<Trail>? = null): List<Trail>

    // Returns trails with event data filtered by:
    //  - time frame: from <= event START ... event END <= to
    suspend fun getTrailEventsAnyPlaceStartedAfterEndedBefore(from: Date, to: Date): List<Trail>

    // Returns trails with event data filtered by:
    //  - time frame: from <= event START ... event START <= to
    suspend fun getTrailEventsAnyPlaceStartedDuring(from: Date, to: Date): List<Trail>

    // Returns trails with event data filtered by:
    //  - time frame: after <= event START ...
    suspend fun getTrailEventsAnyPlaceStartedAfter(after: Date): List<Trail>

    // Returns trails with event data filtered by:
    //  - time frame: ... event START <= before
    suspend fun getTrailEventsAnyPlaceStartedBefore(before: Date): List<Trail>
}

class NullTrailsRepository : TrailsRepository {

    override suspend fun getTrailsAnyPlaceAnyTime(): List<Trail> {
        return emptyList()
    }

    override suspend fun getTrailEventsAnyPlaceAnyTime(trails: List<Trail>?): List<Trail> {
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