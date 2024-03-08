package biz.belcorp.salesforce.core.base

interface BaseMapper<T, R> {
    fun map(model: T): R
}
