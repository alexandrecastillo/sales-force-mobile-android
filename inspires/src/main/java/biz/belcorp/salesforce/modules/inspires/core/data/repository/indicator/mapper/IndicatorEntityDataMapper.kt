package biz.belcorp.salesforce.modules.inspires.core.data.repository.indicator.mapper

import biz.belcorp.salesforce.core.entities.sql.inspires.InspiraIndicadorEntity
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraIndicador

class IndicatorEntityDataMapper {

    fun parseLevelParameterList(list: List<InspiraIndicadorEntity>?): List<InspiraIndicador>? {
        val entity = arrayListOf<InspiraIndicador>()

        list?.forEach {
            entity.add(InspiraIndicador(
                it.id,
                it.campania,
                it.ranking,
                it.destino,
                it.nivel,
                it.puntaje,
                it.puntajeMax,
                it.nombreSE,
                it.campaniaInicioPrograma,
                it.campaniaFinPrograma,
                it.activa,
                it.topeRanking,
                it.imagenDestino))
        }

        return entity
    }

    fun parseLevelParameter(inspiraIndicadorEntity: InspiraIndicadorEntity?): InspiraIndicador? {

        return  inspiraIndicadorEntity?.let {
            InspiraIndicador(
                it.id,
                it.campania,
                it.ranking,
                it.destino,
                it.nivel,
                it.puntaje,
                it.puntajeMax,
                it.nombreSE,
                it.campaniaInicioPrograma,
                it.campaniaFinPrograma,
                it.activa,
                it.topeRanking,
                it.imagenDestino)
        }
    }
}
