package biz.belcorp.salesforce.core.features.base

interface Presenter {

    fun resume() = Unit

    fun pause() = Unit

    fun destroy() = Unit
}
