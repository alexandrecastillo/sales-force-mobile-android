package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.conversacion.focotrabajo

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.GraficoGr

class GraficoGrMapper {

    fun parse(entidad: GraficoGr) =
        GraficoGrModel(
            tipoGrafico = entidad.tipoGrafico,
            titulo = entidad.titulo, valor = entidad.valor
        )
}
