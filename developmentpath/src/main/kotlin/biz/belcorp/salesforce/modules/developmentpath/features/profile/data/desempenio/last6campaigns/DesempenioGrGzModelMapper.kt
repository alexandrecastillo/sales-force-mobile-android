package biz.belcorp.salesforce.modules.developmentpath.features.profile.data.desempenio.last6campaigns

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.desempenio.DesempenioCampanias
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.desempenio.DesempenioModel

class DesempenioGrGzModelMapper {

    fun parse(entidad: DesempenioCampanias) =
        DesempenioModel(
            campania = entidad.campania,
            descripcionProductividad = entidad.estado,
            colorProductividad = obtenerColor(entidad.estado ?: "-")
        )

    private fun obtenerColor(estado: String): DesempenioModel.ColorProductividad {
        return if (estado.toLowerCase() in listOf(
                "-",
                "critica",
                "crítica"
            )
        ) DesempenioModel.ColorProductividad.ROJO
        else DesempenioModel.ColorProductividad.VERDE
    }
}
