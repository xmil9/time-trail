package net.mikelindner.timetrail.db

import androidx.room.Dao
import androidx.room.Query
import net.mikelindner.timetrail.domain.Event
import net.mikelindner.timetrail.domain.Trail

@Dao
interface TrailDao {

    // Returns all trails without event data.
    @Query(
        "SELECT * FROM trails JOIN events ON trails.id = events.trailId ORDER BY `order`"
    )
    suspend fun getTrailsAnyPlaceAnyTime(): List<Trail>

    // Returns all trails with event data.
    @Query(
        "SELECT * FROM trails JOIN events ON trails.id = events.trailId ORDER BY `order`"
    )
    suspend fun getEventsAnyPlaceAnyTime(): Map<Trail, List<Event>>

    // Returns trails with event data for given trail ids.
    @Query(
        "SELECT * FROM trails JOIN events ON trails.id = events.trailId " +
                "WHERE trails.id IN (:trailIds)" +
                "ORDER BY `order`"
    )
    suspend fun getEventsAnyPlaceAnyTime(trailIds: List<Long>): Map<Trail, List<Event>>

    // Returns trails with only the event data that falls into a given time frame:
    //       [event START, event END]
    //  from ^                      ^ to
    @Query(
        "SELECT * FROM trails JOIN events ON trails.id = events.trailId " +
                "WHERE (julianday(events.start) - :from >= 0) AND " +
                "(events.`end` IS NULL OR (julianday(events.`end`) - :to >= 0)) " +
                "ORDER BY `order`"
    )
    suspend fun getEventsAnyPlaceStartedAfterEndedBefore(
        from: Double,
        to: Double
    ): Map<Trail, List<Event>>

    // Returns trails with only the event data that falls into a given time frame:
    //       [event START, event START]
    //  from ^                        ^ to
    @Query(
        "SELECT * FROM trails JOIN events ON trails.id = events.trailId " +
                "WHERE (julianday(events.start) - :from >= 0) AND " +
                "(julianday(events.start) - :to <= 0) " +
                "ORDER BY `order`"
    )
    suspend fun getEventsAnyPlaceStartedDuring(from: Double, to: Double): Map<Trail, List<Event>>

    // Returns trails with only the event data that falls into a given time frame:
    //        [event START, ...
    //  after ^
    @Query(
        "SELECT * FROM trails JOIN events ON trails.id = events.trailId " +
                "WHERE (julianday(events.start) - :after >= 0) " +
                "ORDER BY `order`"
    )
    suspend fun getEventsAnyPlaceStartedAfter(after: Double): Map<Trail, List<Event>>

    // Returns trails with only the event data that falls into a given time frame:
    //       ..., event START]
    //                       ^ before
    @Query(
        "SELECT * FROM trails JOIN events ON trails.id = events.trailId " +
                "WHERE (julianday(events.start) - :before <= 0) " +
                "ORDER BY `order`"
    )
    suspend fun getEventsAnyPlaceStartedBefore(before: Double): Map<Trail, List<Event>>
}