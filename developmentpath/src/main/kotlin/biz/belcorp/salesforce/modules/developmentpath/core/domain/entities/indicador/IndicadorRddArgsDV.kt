package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.indicador

class IndicadorRddArgsDV(
        var visitadasDV: String,
        var visitasPlanificadasDV: String,
        val visitadasGR: String,
        val visitasPlanificadasGR: String,
        val visitadasGZ: String,
        val visitasPlanificadasGZ: String,
        val visitadasSE: String,
        val visitasPlanificadasSE: String
) : IndicatorArgs() {}
