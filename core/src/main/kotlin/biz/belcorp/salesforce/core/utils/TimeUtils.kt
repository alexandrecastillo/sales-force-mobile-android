package biz.belcorp.salesforce.core.utils

import biz.belcorp.salesforce.core.constants.Constant
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

private const val WEEK = 7
private const val MIN_VALUE = 1
private const val MAX_DAYS = 6
private const val MAX_HOURS = 23
private const val MAX_MINUTES = 59

private const val SPECIFIC_FORMAT = "dd MMM yyyy"

private val locale = Locale("es", "PE")
private val format = SimpleDateFormat(SPECIFIC_FORMAT, locale)

private const val SPECIFIC_DESCRIPTION = "specific date"
private const val YEARS_DESCRIPTION = "Año"
private const val WEEKS_DESCRIPTION = "semana"
private const val DAYS_DESCRIPTION = "día"
private const val HOURS_DESCRIPTION = "hora"
private const val MINUTES_DESCRIPTION = "minuto"
private const val SECONDS_DESCRIPTION = "segundo"

sealed class Time {
    object Hours : Time() {
        override fun toString() = HOURS_DESCRIPTION
    }

    object Minutes : Time() {
        override fun toString() = MINUTES_DESCRIPTION
    }

    object Seconds : Time() {
        override fun toString() = SECONDS_DESCRIPTION
    }

    object Days : Time() {
        override fun toString() = DAYS_DESCRIPTION
    }

    object Weeks : Time() {
        override fun toString() = WEEKS_DESCRIPTION
    }

    object Years : Time() {
        override fun toString() = YEARS_DESCRIPTION
    }

    object Specific : Time() {
        override fun toString() = SPECIFIC_DESCRIPTION
    }
}

class ElapsedTime(val count: Long, val unitTime: Time) {
    var description = Constant.EMPTY_STRING
}

fun calculateElapsedTime(timeStamp: Long): ElapsedTime {
    val otherDate = Date(timeStamp)
    val today = Date()
    return calculateDates(today, otherDate)
}

private fun calculateDiffTime(today: Date, otherDate: Date) = today.time - otherDate.time

fun calculateDiffHours(today: Date = Date(), otherDate: Date): Long {
    val diff = calculateDiffTime(today, otherDate)
    return calculateHours(diff)
}

fun calculateDiffMinutes(today: Date = Date(), otherDate: Date): Long {
    val diff = calculateDiffTime(today, otherDate)
    return calculateMinutes(diff)
}

private fun calculateSeconds(diffTime: Long = Constant.NUMBER_ZERO.toLong()) =
    TimeUnit.MILLISECONDS.toSeconds(diffTime)

private fun calculateMinutes(diffTime: Long = Constant.NUMBER_ZERO.toLong()) =
    TimeUnit.MILLISECONDS.toMinutes(diffTime)

private fun calculateHours(diffTime: Long = Constant.NUMBER_ZERO.toLong()) =
    TimeUnit.MILLISECONDS.toHours(diffTime)

private fun calculateDays(today: Date, otherDate: Date) =
    today.differenceInExplicitDays(otherDate).toLong()

private fun calculateWeeks(today: Date, otherDate: Date) = calculateDays(today, otherDate) / WEEK

private fun calculateDates(today: Date, otherDate: Date): ElapsedTime {
    val milliseconds = calculateDiffTime(today, otherDate)
    val weeks = calculateWeeks(today, otherDate)
    val days = calculateDays(today, otherDate)
    val hours = calculateHours(milliseconds)
    val minutes = calculateMinutes(milliseconds)
    val seconds = calculateSeconds(milliseconds)

    if (weeks > MIN_VALUE) return ElapsedTime(weeks, Time.Specific).apply {
        description = format.format(otherDate)
    }
    if (weeks == MIN_VALUE.toLong() && days >= WEEK) return ElapsedTime(weeks, Time.Weeks)
    if (days in MIN_VALUE..MAX_DAYS) return ElapsedTime(days, Time.Days)
    if (hours in MIN_VALUE..MAX_HOURS) return ElapsedTime(hours, Time.Hours)
    return if (minutes in MIN_VALUE..MAX_MINUTES) ElapsedTime(minutes, Time.Minutes)
    else ElapsedTime(seconds, Time.Seconds)
}
