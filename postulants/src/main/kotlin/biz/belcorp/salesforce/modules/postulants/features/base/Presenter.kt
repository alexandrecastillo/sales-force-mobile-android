package biz.belcorp.salesforce.modules.postulants.features.base

interface Presenter {

    fun resume() = Unit

    fun pause() = Unit

    fun destroy() = Unit
}
