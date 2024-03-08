package biz.belcorp.salesforce.modules.inspires.core.data.repository.legendconditions.mapper

import biz.belcorp.salesforce.core.entities.sql.inspires.InspiraCondicionesLeyendaEntity
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraCondicionesLeyenda

class LegendConditionsEntityDataMapper {

    fun parseLevelParameterList(list: List<InspiraCondicionesLeyendaEntity>?): List<InspiraCondicionesLeyenda>? {
        val entity = arrayListOf<InspiraCondicionesLeyenda>()

        list?.forEach {
            entity.add(InspiraCondicionesLeyenda(
                it.codigo,
                it.titulo,
                it.descripcion,
                it.nota))
        }

        return entity
    }

    fun parseLevelParameter(inspiraCondicionesLeyendaEntity: InspiraCondicionesLeyendaEntity?): InspiraCondicionesLeyenda? {

        return  inspiraCondicionesLeyendaEntity?.let {
            InspiraCondicionesLeyenda(
                it.codigo,
                it.titulo,
                it.descripcion,
                it.nota)
        }
    }
}
