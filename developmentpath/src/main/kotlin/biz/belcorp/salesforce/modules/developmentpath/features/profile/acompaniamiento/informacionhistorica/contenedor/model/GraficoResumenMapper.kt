package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.contenedor.model

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.GraficoGr

class GraficoResumenMapper {
    fun map(graficos: List<GraficoGr>): List<GraficoResumenModel> {
        return graficos.map { GraficoResumenModel(it.tipoGrafico) }
    }
}
