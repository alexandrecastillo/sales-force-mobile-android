package biz.belcorp.salesforce.core.utils

import biz.belcorp.salesforce.core.constants.Constant
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.*
import kotlin.collections.HashMap

inline fun <reified T : Any> listByElementsOf(vararg elements: Any): List<T> {
    val mutableList = mutableListOf<T>()
    elements.forEach { element ->
        if (element is T) {
            mutableList += element
        } else if (element is List<*>) {
            mutableList += element.mapNotNull { it as? T }
        }
    }
    return mutableList
}

infix fun <T> MutableIterable<T>.extract(predicate: (T) -> Boolean): List<T> {
    val result = this.filter(predicate)

    this.removeAll(predicate)

    return result
}

fun <T> MutableList<T>.addIfNotNull(value: T?) {
    if (value != null) this.add(value)
}

fun <T> MutableList<T>.addIf(condition: Boolean, value: T) {
    if (condition) this.add(value)
}

fun <T> MutableList<T>.addAllIf(condition: Boolean, value: List<T>) {
    if (condition) this.addAll(value)
}

fun <T> List<T>.excludeFirst(): List<T> {
    return if (size <= Constant.NUMERO_UNO) {
        this
    } else {
        this.slice(Constant.NUMERO_UNO until size)
    }
}


fun <KV> List<KV>.toHashMap(): HashMap<KV, KV> {
    val map = HashMap<KV, KV>()
    forEach { map[it] = it }
    return map
}

fun <T> MutableCollection<T>.update(elements: Collection<T>) {
    clear()
    addAll(elements)
}

fun List<String>.positionOf(value: String): Int {
    return map { it.toLowerCase(Locale.getDefault()) }
        .indexOf(value.toLowerCase(Locale.getDefault()))
}

suspend fun <A, B> Iterable<A>.mapParallel(f: suspend (A) -> B): List<B> =
    coroutineScope {
        map { async { f(it) } }.awaitAll()
    }
