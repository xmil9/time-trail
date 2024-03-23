package net.mikelindner.timetrail.domain

class Time(
    val hour: Int = 0,
    val min: Int = 0,
    val sec: Int = 0
)  : Comparable<Time> {
    init {
        require(hour in 0..23) { "Time.hour must be in range [0, 23]." }
        require(min in 0..59) { "Time.min must be in range [0, 59]." }
        require(sec in 0..59) { "Time.sec must be in range [0, 59]." }
    }

    override fun compareTo(other: Time): Int {
        if (hour != other.hour)
            return hour.compareTo(other.hour)
        if (min != other.min)
            return min.compareTo(other.min)
        return sec.compareTo(other.sec)
    }

    fun isEmpty(): Boolean {
        return hour == 0 && min == 0 && sec == 0
    }

    fun isNotEmpty(): Boolean {
        return !isEmpty()
    }

    override fun toString(): String {
        return Time.toReadableString(this)
    }

    companion object {
        private fun toStringWithSeparator(t: Time, sep: String): String {
            return "%02d".format(t.hour) + sep + "%02d".format(t.min) + sep + "%02d".format(t.sec)
        }

        private fun fromStringWithSeparator(s: String, sep: String): Time {
            val hms = s.split(sep)
            val h = if (hms.size >= 1) hms[0].toInt() else 0
            val m = if (hms.size >= 2) hms[1].toInt() else 0
            val s = if (hms.size >= 3) hms[2].toInt() else 0
            return Time(h, m, s)
        }

        /////////////////////
        // ISO 8601 representation of dates

        private val iso8601Separator = ":"
        private val iso8601Prefix = "T"

        fun toIso8601String(t: Time?, withPrefix: Boolean = false): String? {
            return t?.let {
                val prefix = if (withPrefix) iso8601Prefix else ""
                return prefix + toStringWithSeparator(it, iso8601Separator)
            }
        }

        fun fromIso8601String(s: String?): Time? {
            return s?.let {
                val nonPrefixed = it.substringAfter(iso8601Prefix, it)
                return fromStringWithSeparator(nonPrefixed, iso8601Separator)
            }
        }

        /////////////////////
        // Human readable representation of times

        private val readableSeparator = ":"

        fun toReadableString(t: Time?): String {
            if (t == null || t.isEmpty())
                return ""
            return toStringWithSeparator(t, readableSeparator)
        }

        fun fromReadableString(s: String?): Time {
            if (s.isNullOrEmpty())
                return Time()
            return fromStringWithSeparator(s, readableSeparator)
        }
    }
}
