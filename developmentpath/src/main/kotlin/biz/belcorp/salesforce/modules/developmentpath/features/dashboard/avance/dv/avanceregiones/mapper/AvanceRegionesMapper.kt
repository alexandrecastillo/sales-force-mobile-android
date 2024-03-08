package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avanceregiones.mapper

import biz.belcorp.salesforce.core.utils.WordUtils
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.RegionRdd
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avanceregiones.model.RegionCoberturadaModel
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avanceregiones.model.RegionDescoberturadaModel
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avanceregiones.model.RegionModel

class AvanceRegionesMapper {

    fun parse(regiones: List<RegionRdd>): List<RegionModel> {
        return regiones.map { parse(it) }
    }

    fun parse(region: RegionRdd): RegionModel {
        return if (region.coberturada) {
            parseACoberturada(region)
        } else {
            parseADescoberturada(region)
        }
    }

    private fun parseACoberturada(region: RegionRdd): RegionCoberturadaModel {
        val gerente =  RegionCoberturadaModel.GerenteRegionModel(
                nombre = WordUtils.capitalizeFully((region.gerenteRegion?.nombreCorto ?: "-").toLowerCase()),
                estadoProductividad = region.gerenteRegion?.estadoProductividad ?: "")

        return RegionCoberturadaModel(
                gerenteRegion = gerente,
                codigoCampania = region.campania.nombreCorto,
                llaveUA = region.llave,
                visitasPlanificadas = region.visitasPlanificadas,
                visitasRealizadas = region.visitasRealizadas,
                planId = region.planId,
                planValido = region.planValido)
    }

    private fun parseADescoberturada(region: RegionRdd): RegionDescoberturadaModel {
        return RegionDescoberturadaModel(
                llaveUA = region.llave,
                codigoCampania = region.campania.nombreCorto,
                visitasPlanificadas = region.visitasPlanificadas,
                visitasRealizadas = region.visitasRealizadas,
                planId = region.planId,
                planValido = region.planValido)
    }
}
