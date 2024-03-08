package biz.belcorp.salesforce.core.utils

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.Box
import io.objectbox.Property
import io.objectbox.kotlin.inValues
import io.objectbox.query.QueryBuilder
import io.objectbox.query.QueryBuilder.StringOrder


fun <T> Box<T>.deleteAndInsert(entities: List<T>) {
    store.runInTx {
        removeAll()
        put(entities)
    }
}

inline fun <T> QueryBuilder<T>.doIf(value: Boolean, f: QueryBuilder<T>.() -> Unit) =
    apply {
        if (value) f.invoke(this)
    }

fun <T> QueryBuilder<T>.equal(property: Property<T>, value: Double) =
    apply {
        between(property, value, value)
    }

fun <T> QueryBuilder<T>.equal(property: Property<T>, value: String): QueryBuilder<T> =
    apply {
        equal(property, value, StringOrder.CASE_INSENSITIVE)
    }

fun <T> QueryBuilder<T>.notEqual(property: Property<T>, value: String): QueryBuilder<T> =
    apply {
        notEqual(property, value, StringOrder.CASE_INSENSITIVE)
    }

fun <T> QueryBuilder<T>.isPositive(property: Property<T>) =
    apply {
        greater(property, Constant.ZERO_DECIMAL)
    }

fun <T> QueryBuilder<T>.isZeroOrNegative(property: Property<T>) =
    apply {
        between(property, Constant.ZERO_DECIMAL, Double.NEGATIVE_INFINITY)
    }

fun <T> QueryBuilder<T>.notIn(property: Property<T?>, values: List<String>) =
    apply {
        values.forEachIndexed { i, v ->
            this.notEqual(property, v, StringOrder.CASE_INSENSITIVE)
                .doIf(values.size - 1 != i) {
                    and()
                }
        }
    }

fun <T> QueryBuilder<T>.`in`(property: Property<T>, values: Array<String>): QueryBuilder<T> =
    apply {
        `in`(property, values, StringOrder.CASE_INSENSITIVE)
    }

inline fun <reified T> QueryBuilder<T>.inValues(
    property: Property<T>,
    values: Array<String>
): QueryBuilder<T> =
    apply {
        inValues(property, values, StringOrder.CASE_INSENSITIVE)
    }

fun <T> QueryBuilder<T>.contains(property: Property<T>, value: String): QueryBuilder<T> =
    apply {
        contains(property, value, StringOrder.CASE_INSENSITIVE)
    }
