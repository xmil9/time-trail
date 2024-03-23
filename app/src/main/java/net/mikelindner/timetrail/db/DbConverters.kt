package net.mikelindner.timetrail.db

import androidx.room.TypeConverter
import net.mikelindner.timetrail.domain.Time
import net.mikelindner.timetrail.domain.Date

class DbConverters {
    @TypeConverter
    public fun toDate(dbValue: String?): Date? {
        return Date.fromIso8601String(dbValue)
    }

    @TypeConverter
    public fun fromDate(date: Date?): String? {
        return Date.toIso8601String(date)
    }

    @TypeConverter
    public fun toTime(dbValue: String?): Time? {
        return Time.fromIso8601String(dbValue)
    }

    @TypeConverter
    public fun fromTime(time: Time?): String? {
        return Time.toIso8601String(time, false)
    }
}