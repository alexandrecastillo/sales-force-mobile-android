package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.ventas

class GainConsultantContainer(val gains: List<GainConsultant>) {
    var bestGain: GainConsultant? = null
    var lastGain: GainConsultant? = null

    val gainBest get() = requireNotNull(bestGain)
    val gainLast get() = requireNotNull(lastGain)
}

class GainConsultant(val campaign: String, val amount: Double)
