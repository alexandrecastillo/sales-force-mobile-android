package biz.belcorp.salesforce.core.utils

import biz.belcorp.salesforce.core.constants.Constant
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

const val formatShort = "yyyy-MM-dd"
const val formatLongT = "yyyy-MM-dd'T'HH:mm:ss"
const val formatLongTZ = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSZZZZZ"
const val formatLong = "yyyy-MM-dd HH:mm:ss"
const val formatShort2 = "dd/MM/yyyy"
const val formatShort4 = "dd/MM/yy"
const val formatTime = "HH:mm:ss"
const val formatShort3 = "yyyyMMdd"
const val formatDescriptionDay = "d 'de' MMMM"
const val formatDescriptionDayYEAR = "d 'de' MMMM 'de' yyyy"
const val formatDate = "EEEE',' d 'de' MMMM "
const val formatHoursDescription = "hh:mm a"
const val formatHourAmPm = "hh"
const val formatOnlyHour = "HH"
const val formatOnlyMinutes = "mm"
const val formatLongest = "  dd  /  MM  /  yyyy  "
const val formatDescriptionDayPlusHours = "EEEE d 'de' MMMM hh:mm a"
const val formatShortDescriptionDayMonth = "EEE, d MMM"
const val formatLongDescriptionDayMonthYear = "EEEE, d MMMM 'de' yyyy"
const val formatLongDescriptionDayMonthYear2 = "EEEE d 'de' MMMM 'de' yyyy"
const val formatDayMonth = "dd/MM"
const val formatHourMinAmPm = "hh:mm a"
const val formatShortInverted = "dd-MM-yyyy"
const val formatLongInverted = "dd-MM-yyyy HH:mm:ss"
const val formatMinutesAndSeconds = "mm:ss"

private val esLocale = Locale("es", "ES")
const val timeZone = "GMT"

private const val ZERO = 0

fun Date.string(format: String, locale: String? = null): String? {
    val df = SimpleDateFormat(format, locale?.let { Locale(it) } ?: Locale("es"))
    return df.format(this)
}

fun String.toDate(format: String = formatShort): Date? {
    return toDate(format, locale = null)
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

fun String.toDateWithT(): Date? {
    return toDate(formatLongT) ?: toDate(formatLong)
}

fun Date.toDescriptionDay(): String? {
    return string(formatDescriptionDay)
}

fun Date.toDescriptionDayYear(): String? {
    return string(formatDescriptionDayYEAR)
}

fun String.changeDateFormat(format: String, newFormat: String): String? {
    return toDate(format)?.string(newFormat)
}

fun String.toFullDate(): Date? {
    return toDate(formatLong)
}

fun Date.toCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar
}

infix fun Date.before(fecha: Date): Boolean {
    return this.before(fecha)
}

infix fun Date.after(fecha: Date): Boolean {
    return this.after(fecha)
}

fun Date.finDelDia(): Date {
    val calendar = this.toCalendar()
    return calendar.finDelDia().time
}

infix fun Date.differenceInDays(toCompare: Date): Int {
    val now = this.toCalendar().toGlobalLocale()
    val compare = (now.clone() as Calendar)
    compare.time = toCompare
    val diffTime = now.timeInMillis - compare.timeInMillis

    return TimeUnit.DAYS.convert(diffTime, TimeUnit.MILLISECONDS).toInt()
}

infix fun Date.differenceInExplicitDays(toCompare: Date): Int {
    val now = this.toCalendar()
    now.set(Calendar.HOUR, Constant.NUMBER_ZERO)
    now.set(Calendar.MINUTE, Constant.NUMBER_ZERO)
    now.set(Calendar.SECOND, Constant.NUMBER_ZERO)
    now.set(Calendar.MILLISECOND, Constant.NUMBER_ZERO)

    val compare = toCompare.toCalendar()
    compare.set(Calendar.HOUR, Constant.NUMBER_ZERO)
    compare.set(Calendar.MINUTE, Constant.NUMBER_ZERO)
    compare.set(Calendar.SECOND, Constant.NUMBER_ZERO)
    compare.set(Calendar.MILLISECOND, Constant.NUMBER_ZERO)

    val diffTime = now.timeInMillis - compare.timeInMillis

    return TimeUnit.MILLISECONDS.toDays(diffTime).toInt()
}

fun Date.aumentarMinutosSinCambiarDia(minutos: Int): Date {
    val horaOriginal = this.toCalendar()
    val horaAumentada = (horaOriginal.clone() as Calendar)

    horaAumentada.add(Calendar.MINUTE, minutos)

    if (!horaOriginal.es(horaAumentada)) {
        horaOriginal.set(Calendar.HOUR_OF_DAY, 23)
        horaOriginal.set(Calendar.MINUTE, 59)
        return horaOriginal.time
    }

    return horaAumentada.time
}

fun Date.redondearMinutosHaciaArriba(multiplo: Int): Date {
    val hora = this.toCalendar()
    hora.set(Calendar.SECOND, ZERO)
    hora.set(Calendar.MILLISECOND, ZERO)

    val residuo = hora.get(Calendar.MINUTE).rem(multiplo)

    if (residuo == ZERO) return hora.time

    val restante = multiplo - residuo
    hora.add(Calendar.MINUTE, restante)

    return hora.time
}


fun Date.parseToDateItem(): String {
    val simpleDate = SimpleDateFormat(formatDescriptionDay, esLocale)
    return simpleDate.format(this)
}

fun Calendar.parseToDateItem(): String {
    val simpleDate = SimpleDateFormat(formatDescriptionDay, esLocale)
    return simpleDate.format(this.time)
}

fun Date.parseToDateString(): String {
    val simpleDate = SimpleDateFormat(formatDate, esLocale)
    return simpleDate.format(this)
}

fun Date.parseToShortString(): String {
    val simpleDate = SimpleDateFormat(formatShort, esLocale)
    return simpleDate.format(this)
}

fun Date.parseToShortYearString(): String {
    val simpleDate = SimpleDateFormat(formatShort4, Locale.getDefault())
    return simpleDate.format(this)
}

fun Date.parseToDescription(): String {
    val simpleDate = SimpleDateFormat(formatLongDescriptionDayMonthYear2, esLocale)
    return simpleDate.format(this)
}

fun Date.parseToDescriptionPlusHours(): String {
    val simpleDate = SimpleDateFormat(formatDescriptionDayPlusHours, esLocale)
    return simpleDate.format(this)
}

fun Date.parseToHoursDescription(): String {
    val simpleDate = SimpleDateFormat(formatHoursDescription, esLocale)
    return simpleDate.format(this)
}

fun Date.parseToLongestString(): String {
    val simpleDate = SimpleDateFormat(formatLongest, esLocale)
    return simpleDate.format(this)
}

fun Date.parseToHourAmPm(): String {
    val simpleDate = SimpleDateFormat(formatHourAmPm, esLocale)
    return simpleDate.format(this)
}

fun Date.parseToHour(): String {
    val simpleDate = SimpleDateFormat(formatOnlyHour, esLocale)
    return simpleDate.format(this)
}

fun Date.parseToMinutes(): String {
    val simpleDate = SimpleDateFormat(formatOnlyMinutes, esLocale)
    return simpleDate.format(this)
}

fun Date.parseToTriple(): Triple<String, String, String> {
    val date = this.toCalendar()
    val year = date.get(Calendar.YEAR)
    val month = date.get(Calendar.MONTH) + Constant.NUMBER_ONE
    val day = date.get(Calendar.DAY_OF_MONTH)
    return Triple(day.toString(), month.toString(), year.toString())
}

fun Date.parseToShortDate(): Date {
    val sdf = SimpleDateFormat(formatShort, esLocale)
    return sdf.parse(this.parseToShortString())
}

fun Date.parseToShortDescripitionDayMonth(): String {
    val simpleDate = SimpleDateFormat(formatShortDescriptionDayMonth, esLocale)
    return simpleDate.format(this).capitalize()
}

fun Date.parseToLongDescripitionDayMonthYear(): String {
    val simpleDate = SimpleDateFormat(formatLongDescriptionDayMonthYear, esLocale)
    return simpleDate.format(this).capitalize()
}

fun Date.es(date: Date): Boolean {
    val dateA = this.parseToShortDate().time
    val dateB = date.parseToShortDate().time
    return dateA == dateB
}

fun Date.parseToHourMinAmPmString(): String {
    val simpleDate = SimpleDateFormat(formatHourMinAmPm, esLocale)
    return simpleDate.format(this).deleteDot().deleteSpace()
}

fun Date.parseToDayMonthString(): String {
    val simpleDate = SimpleDateFormat(formatDayMonth, esLocale)
    return simpleDate.format(this)
}

fun Date.horaFormateada(): String {
    val df = SimpleDateFormat(formatHoursDescription, Locale.getDefault())

    return df.format(this).toLowerCase()
}
