package biz.belcorp.salesforce.modules.inspires.core.data.repository.ranking.mapper

import biz.belcorp.salesforce.core.entities.sql.inspires.InspiraRankingEntity
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraRanking

class RankingEntityDataMapper {

    fun parseLevelParameterList(list: List<InspiraRankingEntity>?): List<InspiraRanking>? {
        val entity = arrayListOf<InspiraRanking>()

        list?.forEach {
            entity.add(InspiraRanking(
                it.id,
                it.puesto,
                it.usuario,
                it.puntaje,
                it.flagStatus,
                it.isFlagEsUsuario,
                it.bloque))
        }

        return entity
    }

    fun parseLevelParameter(inspiraRankingEntity: InspiraRankingEntity?): InspiraRanking? {

        return  inspiraRankingEntity?.let {
            InspiraRanking(
                it.id,
                it.puesto,
                it.usuario,
                it.puntaje,
                it.flagStatus,
                it.isFlagEsUsuario,
                it.bloque)
        }
    }
}
