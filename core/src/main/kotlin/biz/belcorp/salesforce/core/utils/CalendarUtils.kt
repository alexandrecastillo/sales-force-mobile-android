package biz.belcorp.salesforce.core.utils


import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import java.util.*

fun Calendar?.es(toCompare: Calendar) =
    this != null &&
        this.get(Calendar.DAY_OF_YEAR) == toCompare.get(Calendar.DAY_OF_YEAR) &&
        this.get(Calendar.YEAR) == toCompare.get(Calendar.YEAR)

fun Calendar.perteneceAMes(toCompare: Calendar) =
    this.get(Calendar.MONTH) == toCompare.get(Calendar.MONTH) &&
        this.get(Calendar.YEAR) == toCompare.get(Calendar.YEAR)

fun Calendar.esFinDeSemana() =
    this.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
        this.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY

fun Calendar.diferenciaDeMesesCon(toCompare: Calendar): Int {
    val diferenciaDeAnios = this.get(Calendar.YEAR) - toCompare.get(Calendar.YEAR)

    return diferenciaDeAnios * 12 + this.get(Calendar.MONTH) - toCompare.get(Calendar.MONTH)
}

fun Calendar.esAnteriorA(toCompare: Calendar): Boolean {

    return this.before(toCompare) && !this.es(toCompare)
}

fun Calendar.esPosteriorA(toCompare: Calendar): Boolean {

    return this.after(toCompare) && !this.es(toCompare)
}

fun Calendar.inicioDelDia(): Calendar {
    val inicio = this.clone() as Calendar
    inicio.set(Calendar.HOUR_OF_DAY, 0)
    inicio.set(Calendar.MINUTE, 0)
    inicio.set(Calendar.SECOND, 0)
    inicio.set(Calendar.MILLISECOND, 0)
    return inicio
}

fun Calendar.finDelDia(): Calendar {
    val fin = this.clone() as Calendar
    fin.set(Calendar.HOUR_OF_DAY, 23)
    fin.set(Calendar.MINUTE, 59)
    fin.set(Calendar.SECOND, 59)
    fin.set(Calendar.MILLISECOND, 999)
    return fin
}

fun Calendar.differenceInDays(toCompare: Calendar): Int {
    return this.time.differenceInDays(toCompare.time)
}

fun Calendar.toGlobalLocale(): Calendar {
    val localeCalendar = Calendar.getInstance(Locale.US)
    localeCalendar.time = this.time
    return localeCalendar
}

val Calendar.year get() = get(Calendar.YEAR)
val Calendar.month get() = get(Calendar.MONTH)
val Calendar.weekOfYear get() = get(Calendar.WEEK_OF_YEAR)
val Calendar.dayOfYear get() = get(Calendar.DAY_OF_YEAR)
val Calendar.dayOfMonth get() = get(Calendar.DAY_OF_MONTH)
val Calendar.dayOfWeek get() = get(Calendar.DAY_OF_WEEK)
val Calendar.hour get() = get(Calendar.HOUR)
val Calendar.hourOfDay get() = get(Calendar.HOUR_OF_DAY)
val Calendar.minute get() = get(Calendar.MINUTE)
val Calendar.second get() = get(Calendar.SECOND)
val Calendar.lastDayOfMonth get() = getActualMaximum(Calendar.DAY_OF_MONTH)

fun Calendar.minusYears(years: Int, countryIso: String? = null) = apply {
    add(Calendar.YEAR, -years)

    when (countryIso) {
        Pais.ECUADOR.codigoIso -> {
            add(Calendar.DAY_OF_YEAR, 2)
        }
    }
}

fun Calendar.isToday(): Boolean {
    val today = Calendar.getInstance()
    return isSameDayAsDate(today)
}

fun Calendar.isYesterday(): Boolean {
    val yesterday = Calendar.getInstance().apply {
        add(Calendar.DAY_OF_MONTH, -1)
    }
    return isSameDayAsDate(yesterday)
}

fun Calendar.isSameDayAsDate(calendar: Calendar): Boolean {
    return (year == calendar.year) &&
        (month == calendar.month) &&
        (dayOfMonth == calendar.dayOfMonth)
}

fun Calendar.addOneMonth() = apply {
    add(Calendar.MONTH, 1)
}

fun Calendar.aproximarAUltimoInstanteDelDia(): Calendar {
    set(Calendar.HOUR_OF_DAY, 23)
    set(Calendar.MINUTE, 59)
    set(Calendar.SECOND, 59)
    set(Calendar.MILLISECOND, 999)

    return this
}
