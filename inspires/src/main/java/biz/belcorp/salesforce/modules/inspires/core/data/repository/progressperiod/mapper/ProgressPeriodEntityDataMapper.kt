package biz.belcorp.salesforce.modules.inspires.core.data.repository.progressperiod.mapper

import biz.belcorp.salesforce.core.entities.sql.inspires.InspiraAvancesPeriodoEntity
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraAvancesPeriodo

class ProgressPeriodEntityDataMapper {

    fun parseLevelParameterList(list: List<InspiraAvancesPeriodoEntity>?): List<InspiraAvancesPeriodo>? {
        val entity = arrayListOf<InspiraAvancesPeriodo>()

        list?.forEach {
            entity.add(InspiraAvancesPeriodo(
                it.codigoPeriodo,
                it.periodo,
                it.campaniaFinPeriodo,
                it.puntaje))
        }

        return entity
    }

    fun parseLevelParameter(inspiraAvancesPeriodoEntity: InspiraAvancesPeriodoEntity?): InspiraAvancesPeriodo? {

        return  inspiraAvancesPeriodoEntity?.let {
            InspiraAvancesPeriodo(
                it.codigoPeriodo,
                it.periodo,
                it.campaniaFinPeriodo,
                it.puntaje)
        }
    }
}
