package biz.belcorp.salesforce.core.utils

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.HYPHEN
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ONE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ONE_HUNDRED
import biz.belcorp.salesforce.core.constants.Constant.PLUS
import biz.belcorp.salesforce.core.constants.Constant.PRECISION_LOSS
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor
import kotlin.math.round

fun Int?.ceroSiNull() = this ?: Constant.NUMERO_CERO

fun Int?.negativoSiNull() = this ?: Constant.ONE_NEGATIVE

fun Long?.negativoSiNull() = this ?: Constant.ONE_NEGATIVE.toLong()

fun Double.redondearDosDecimales() = round(this * 100.00) / 100.00

fun Double.numberFormatUS() = NumberFormat.getNumberInstance(Locale.US).format(this)

fun Float.formatearConComas() = String.format(Locale.US, "%,.2f", this)

fun Double.formatWithThousands() = String.format(Locale.US, "%,.1f", this)

fun Double.formatWithLowerThousands() = truncateLowerOneDecimal().formatWithThousands()

fun Double.formatWithNoDecimalThousands() = String.format(Locale.US, "%,.0f", this)

fun Float.formatWithNoDecimalThousands() = String.format(Locale.US, "%,.0f", this)
fun Float.formatWithDecimalThousands() = String.format(Locale.US, "%,.1f", this)

fun Double.doubleOrInt(): String {
    return if (this - floor(this) == 0.0) {
        this.toInt().toString()
    } else {
        this.toString()
    }
}

fun Int.formatWithThousands() = String.format(Locale.US, "%,d", this)

fun Double.truncateLowerOneDecimal(): Double {
    val scale = BigDecimal.valueOf(this).scale()
    return if (scale > NUMBER_ONE)
        BigDecimal(this).setScale(NUMBER_ONE, RoundingMode.DOWN).toDouble()
    else this
}

fun Double.truncateLowerInt() =
    BigDecimal(this).setScale(Constant.NUMBER_ZERO, RoundingMode.DOWN).toInt()

fun Double.formatTruncateIntThousandsLabel() = this.truncateLowerInt().formatWithThousands()

fun Double.formatOneDecimal() = String.format(Locale.US, "%,.1f", this)

fun Double.withPercentage() = this.toString().plus(Constant.PERCENTAGE)

fun Int?.zeroIfNull() = this ?: Constant.NUMBER_ZERO

fun Float.toHundredPercentage() = this * NUMBER_ONE_HUNDRED

fun Float?.zeroIfNull() = this ?: Constant.NUMBER_ZERO.toFloat()

fun Double?.zeroIfNull() = this ?: Constant.ZERO_DECIMAL

fun Long.isNegative() = this < Constant.NUMBER_ZERO.toLong()
fun Long.isPositive() = this > Constant.NUMBER_ZERO.toLong()
fun Long.isZero() = this == Constant.NUMBER_ZERO.toLong()

fun Int.isNegative() = this < Constant.NUMBER_ZERO
fun Int.isPositive() = this > Constant.NUMBER_ZERO
fun Int.isZero() = this == Constant.NUMBER_ZERO

fun Double.isNegative() = this < Constant.ZERO_DECIMAL
fun Double.isPositive() = this > Constant.ZERO_DECIMAL

fun Double.toPercentageTruncateDecimal() =
    (this * NUMBER_ONE_HUNDRED + PRECISION_LOSS).truncateLowerOneDecimal()

fun Double.toPercentageNumber(): Int = (this * NUMBER_ONE_HUNDRED).toInt()
fun Double.toPercentageFormat(): String = (this * NUMBER_ONE_HUNDRED).formatOneDecimal()
fun Double.toPercentageWithLowerThousands(): String =
    (this * NUMBER_ONE_HUNDRED).formatWithLowerThousands()

fun Long.toDateString(): String {
    val date = Date(this)
    val format = SimpleDateFormat("dd/MM hh:mm aa")
    return format.format(date)
}

fun Double.doubleOrIntFormatWithCommas(): String {
    return if (this - Math.floor(this) == 0.0) {
        this.toInt().formatWithThousands()
    } else {
        this.formatearConComas()
    }
}

fun Double.formatearConComas(): String {
    return String.format(Locale.US, "%,.2f", this)
}

fun Float.isNaNToZero(): Float {
    if (this.isNaN())
        return 0f
    return this
}

fun Double.isNaNToZero(): Double {
    if (this.isNaN())
        return 0.0
    return this
}

fun Double.compareWithNumber(numberCompare: Double, resultTrue: Double): Double {
    return if (this == numberCompare) {
        resultTrue
    } else {
        this
    }
}

fun Int.formatWithMathematicalSymbol(): String {
    return when {
        this.isPositive() -> PLUS.plus(this)
        this.isNegative() -> HYPHEN.plus(this)
        else -> this.toString()
    }
}

fun Int?.toStringOrNull(): String? {
    return try {
        this.toString().takeIf { it.isNotBlank() && it != Constant.NULL_STRING }
    } catch (e: Exception) {
        null
    }
}

fun Float.getSafePercentage(number: Float): Int {
    return if (this.isNotNull() && number.isNotNull() && number > 0) {
        this.div(number).toHundredPercentage().toInt()
    } else 0
}
