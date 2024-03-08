package biz.belcorp.salesforce.modules.developmentpath.core.data.utils

import biz.belcorp.salesforce.core.utils.formatLong
import biz.belcorp.salesforce.core.utils.formatLongT
import biz.belcorp.salesforce.core.utils.formatShort
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

fun String?.toDate(): Date? {
    if (this.isNullOrEmpty()) return null
    val df = SimpleDateFormat(formatShort, Locale.getDefault())

    return df.parse(this)
}

fun String?.toLongDate(): Date? {
    if (isNullOrEmpty()) return null
    val df = SimpleDateFormat(formatLong, Locale.getDefault())
    return df.parse(this)
}

fun String?.toDateFromShort(): Date? {
    if (this.isNullOrBlank()) return null
    val df = SimpleDateFormat(formatShort, Locale.getDefault())

    return df.parse(this)
}

fun Calendar.toStringDate(): String {
    val simpleDate = SimpleDateFormat(formatLong, Locale.getDefault())
    return simpleDate.format(this.time)
}

fun String?.toDateT(): Date? {
    return if (this != null) {
        val df =
            when {
                this.contains("T", ignoreCase = true) ->
                    SimpleDateFormat(formatLongT, Locale.getDefault())
                this.length > 10 ->
                    SimpleDateFormat(formatLong, Locale.getDefault())
                else ->
                    SimpleDateFormat(formatShort, Locale.getDefault())
            }
        df.parse(this)
    } else {
        null
    }
}

fun String?.arregloVacioSiNull() = this ?: "[]"

fun String?.toCalendar(): Calendar? {
    if (this == null) return null
    val calendar = Calendar.getInstance()
    this.toDateT()?.let { calendar.time = it }

    return calendar
}

fun String?.toCalendarAnotacion(): Calendar {
    val calendar = Calendar.getInstance()

    this?.apply {
        val tiempo = SimpleDateFormat(formatLong, Locale.getDefault()).parse(this)
        tiempo?.let { calendar.time = it }
    }
    return calendar
}

fun Calendar.toLongString() = this.time.toLongString()

fun Date.toLongString(): String {
    val df = SimpleDateFormat(formatLong, Locale.getDefault())

    return df.format(this)
}

fun Date.toShortString(): String {
    val df = SimpleDateFormat(formatShort, Locale.getDefault())

    return df.format(this)
}


fun Long?.ceroSiNull() = this ?: 0L

fun Int?.ceroSiNull() = this ?: 0

fun Calendar.toShortString() = this.time.toShortString()

fun String.toPair(): Pair<Double, Double> {
    var first = 0.0
    var second = 0.0
    if (this.isNotEmpty() && this.contains(",", true)) {
        val lista = this.split(",")
        if (lista.size > 1) {
            first = lista[0].toDouble()
            second = lista[1].toDouble()
        }
    }
    return Pair(first, second)
}

fun Pair<Double, Double>.calcularDistancia(punto: Pair<Double, Double>): Double {
    val radioTierra = 6371000.0f
    val dLat = Math.toRadians(this.first - punto.first)
    val dLng = Math.toRadians(this.second - punto.second)
    val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(Math.toRadians(this.first)) *
        Math.cos(Math.toRadians(punto.first)) *
        Math.sin(dLng / 2) * Math.sin(dLng / 2)
    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

    return (radioTierra * c)
}

inline fun <reified T> String?.toArrayOf(): Array<T> {
    return Gson().fromJson(this ?: Constant.EMPTY_ARRAY, Array<T>::class.java)
}

