package biz.belcorp.salesforce.modules.inspires.core.data.repository.conditionslegenddetail.mapper

import biz.belcorp.salesforce.core.entities.sql.inspires.InspiraCondicionesLeyendaDetalleEntity
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraCondicionesLeyendaDetalle

class ConditionsLegendDetailEntityDataMapper {

    fun parseLevelParameterList(list: List<InspiraCondicionesLeyendaDetalleEntity>?): List<InspiraCondicionesLeyendaDetalle>? {
        val entity = arrayListOf<InspiraCondicionesLeyendaDetalle>()

        list?.forEach {
            entity.add(InspiraCondicionesLeyendaDetalle(
                it.id,
                it.codigo,
                it.rango,
                it.puntos))
        }

        return entity
    }

    fun parseLevelParameter(inspiraCondicionesLeyendaDetalle: InspiraCondicionesLeyendaDetalleEntity?): InspiraCondicionesLeyendaDetalle? {

        return  inspiraCondicionesLeyendaDetalle?.let {
            InspiraCondicionesLeyendaDetalle(
                it.id,
                it.codigo,
                it.rango,
                it.puntos)
        }
    }
}
