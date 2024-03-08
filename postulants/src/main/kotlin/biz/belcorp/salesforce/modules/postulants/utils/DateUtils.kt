package biz.belcorp.salesforce.modules.postulants.utils

import biz.belcorp.salesforce.core.utils.toCalendar
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

const val formatLongT = "yyyy-MM-dd'T'HH:mm:ss"
const val formatLongTZ = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSZZZZZ"
const val formatShort2 = "dd/MM/yyyy"
const val timeZone = "GMT"

fun Date.string(format: String, locale: String? = null): String? {
    val df = SimpleDateFormat(format, locale?.let { Locale(it) } ?: Locale("es"))
    return df.format(this)
}

fun String.toDate(format: String, locale: String? = null): Date? {
    if (this.isBlank()) return null
    return try {
        val df = SimpleDateFormat(format, locale?.let { Locale(it) } ?: Locale("es"))
        if (format == formatLongTZ) {
            df.timeZone = TimeZone.getTimeZone(timeZone)
        }
        df.parse(this)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Date.daysBeforeNow(): Int {
    val timeInterval = System.currentTimeMillis() - time
    return TimeUnit.MILLISECONDS.toDays(timeInterval).toInt()
}

fun Date.yearsBeforeNow(): Int {
    val calendarStart = toCalendar()
    val today = Calendar.getInstance()
    var years = today.year - calendarStart.year
    if (today.month < calendarStart.month) {
        years--
    } else if (today.month == calendarStart.month
        && today.dayOfMonth < calendarStart.dayOfMonth
    ) {
        years--
    }
    return years
}

val Calendar.year get() = get(Calendar.YEAR)
val Calendar.month get() = get(Calendar.MONTH)
val Calendar.dayOfMonth get() = get(Calendar.DAY_OF_MONTH)

fun Calendar.minusYears(years: Int) = apply {
    add(Calendar.YEAR, -years)
}

fun Date.calcularEdad(): Int {
    return yearsBeforeNow()
}
