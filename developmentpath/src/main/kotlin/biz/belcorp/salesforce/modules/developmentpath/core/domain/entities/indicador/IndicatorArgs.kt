package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.indicador

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
