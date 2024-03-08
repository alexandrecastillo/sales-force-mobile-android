package biz.belcorp.salesforce.modules.inspires.core.data.repository.advances.mapper

import biz.belcorp.salesforce.core.entities.sql.inspires.InspiraAvancesEntity
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraAvances

class AdvancesEntityDataMapper {

    fun parseLevelParameterList(list: List<InspiraAvancesEntity>?): List<InspiraAvances>? {
        val entity = arrayListOf<InspiraAvances>()

        list?.forEach {
            entity.add(InspiraAvances(
                it.campania,
                it.puntosAcumulados,
                it.statusNivel,
                it.porcRetencionActivas,
                it.actividad,
                it.rangoActividad,
                it.porcMontoPedido,
                it.porcPAV,
                it.capitalizacion,
                it.retencion))
        }

        return entity
    }

    fun parseLevelParameter(inspiraAvances: InspiraAvancesEntity?): InspiraAvances? {

        return  inspiraAvances?.let {
            InspiraAvances(
                it.campania,
                it.puntosAcumulados,
                it.statusNivel,
                it.porcRetencionActivas,
                it.actividad,
                it.rangoActividad,
                it.porcMontoPedido,
                it.porcPAV,
                it.capitalizacion,
                it.retencion)
        }
    }
}
