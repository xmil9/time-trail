package net.mikelindner.timetrail.domain

import java.time.LocalDate
import java.time.temporal.JulianFields

class Date(
    val year: Int,
    val month: Int = 0,
    val day: Int = 0,
    val time: Time = Time()
) : Comparable<Date> {

    init {
        require(month in 0..12) {
            "Date.month is optional but if given must be in [1, 12]."
        }
        // todo - validate day range depending on month and year
        require(day in 0..31) {
            "Date.day is optional but if given must be in [1, 31]."
        }
    }

    override fun compareTo(other: Date): Int {
        if (year != other.year)
            return year.compareTo(other.year)
        if (month != other.month)
            return month.compareTo(other.month)
        if (day != other.day)
            return day.compareTo(other.day)
        return time.compareTo(other.time)
    }

    override fun toString(): String {
        return toReadableString(this)
    }

    fun nonZeroMonth(): Int {
        return if (month == 0) 1 else month
    }

    fun nonZeroDay(): Int {
        return if (day == 0) 1 else day
    }

    companion object {
        private fun toStringWithSeparator(d: Date, sep: String, withEmptyTime: Boolean): String {
            var s = "%04d".format(d.year) +
                    sep +
                    "%02d".format(d.month) +
                    sep +
                    "%02d".format(d.day)
            if (withEmptyTime || d.time.isNotEmpty())
                s += sep + Time.toIso8601String(d.time)
            return s
        }

        private fun fromStringWithSeparator(s: String, sep: String): Date? {
            val fields = s.split(sep)
            if (fields.isEmpty())
                return null

            val y = fields[0].toInt()
            val m = if (fields.size >= 2) fields[1].toInt() else 0
            val d = if (fields.size >= 3) fields[2].toInt() else 0
            val t: Time = if (fields.size > 4)
                Time.fromIso8601String(
                    fields.subList(3, fields.size - 1).joinToString(sep)
                )!!
            else
                Time()

            // Note - Invalid values for each field will be detected during date construction.
            return Date(y, m, d, t)
        }

        /////////////////////
        // ISO 8601 representation of dates

        private const val iso8601Separator = "-"

        fun toIso8601String(d: Date?): String? {
            return d?.let { toStringWithSeparator(it, iso8601Separator, false) }
        }

        fun fromIso8601String(s: String?): Date? {
            return s?.let { fromStringWithSeparator(it, iso8601Separator) }
        }

        /////////////////////
        // Human readable representation of dates

        private val monthReadable =
            arrayOf(
                "Jan",
                "Feb",
                "Mar",
                "Apr",
                "May",
                "Jun",
                "Jul",
                "Aug",
                "Sep",
                "Oct",
                "Nov",
                "Dec"
            )

        private fun monthToReadableString(month: Int): String {
            if (month in 1..12)
                return monthReadable[month - 1]
            return ""
        }

        private fun monthFromReadableString(month: String): Int {
            // When not found -1 is correctly converted to 0.
            return monthReadable.indexOf(month) + 1
        }

        fun toReadableString(d: Date?): String {
            if (d == null)
                return ""

            var s = if (d.day == 0) "" else d.day.toString()
            if (d.month != 0)
                s += " ${monthToReadableString(d.month)}"
            s += " ${d.year}"

            return s
        }

        fun fromReadableString(s: String?): Date? {
            if (s.isNullOrEmpty())
                return null

            val fields = s.split(" ")
            if (fields.isEmpty())
                return null

            var idx = 0
            val d = if (fields.size >= 3) fields[idx++].toInt() else 0
            val m = if (fields.size >= 2) monthFromReadableString(fields[idx++]) else 0
            val y = fields[idx].toInt()
            // Ignore time for now.
            val t = Time()

            // Note - Invalid values for each field will be detected during date construction.
            return Date(y, m, d, t)
        }

        /////////////////////

        fun julianDays(d: Date): Double {
            val ld = LocalDate.of(d.year, d.nonZeroMonth(), d.nonZeroDay())
            return ld.getLong(JulianFields.JULIAN_DAY).toDouble()
        }
    }
}
