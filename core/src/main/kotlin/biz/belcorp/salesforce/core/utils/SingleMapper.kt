package biz.belcorp.salesforce.core.utils

abstract class SingleMapper<in T, out R> {

    abstract fun map(value: T): R

    fun map(values: List<T>): List<R> {
        return values.map(::map)
    }
}
