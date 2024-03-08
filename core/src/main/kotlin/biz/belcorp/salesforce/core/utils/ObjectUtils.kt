package biz.belcorp.salesforce.core.utils

import java.io.Serializable

fun Any?.isNull() = this == null
fun Any?.isNotNull() = this != null
data class Quadruple<out A, out B, out C, out D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
) : Serializable {

    override fun toString() = "($first, $second, $third, $fourth)"
}

fun <T> Quadruple<T, T, T, T>.toList(): List<T> =
    listOf(first, second, third, fourth)