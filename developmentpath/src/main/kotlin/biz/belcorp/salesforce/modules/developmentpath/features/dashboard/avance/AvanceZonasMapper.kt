package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.ZonaRdd
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gr.model.AvanceZonaModel

class AvanceZonasMapper {

    private fun parse(zona: ZonaRdd): AvanceZonaModel {

        return AvanceZonaModel(
                llaveUA = zona.llave,
                nombreGerente =
                "${zona.gerenteZona?.primerNombre ?: ""} ${zona.gerenteZona?.primerApellido ?: ""}",
                nombreCortoCampania = zona.campania.nombreCorto,
                visitasRealizadas = zona.visitasRegistradas,
                visitasPlanificadas = zona.visitasPlanificadas,
                esNueva = zona.gerenteZona?.esNueva ?: false,
                estadoProductividad = zona.gerenteZona?.estadoProductividad,
                estaCoberturada = zona.coberturada,
                planId = zona.planId,
                zonaPlanificada = false
                )
    }

    fun parse(zonas: List<ZonaRdd>) = zonas.map { parse(it) }
}
