package biz.belcorp.salesforce.modules.brightpath.core.domain.entities


open class IndicatorArgs {

    var isWatchAsThirdPerson: Boolean = false


    fun asFirstPerson(): IndicatorArgs {
        isWatchAsThirdPerson = false
        return this
    }

    fun asThirdPerson(): IndicatorArgs {
        isWatchAsThirdPerson = true
        return this
    }
}
