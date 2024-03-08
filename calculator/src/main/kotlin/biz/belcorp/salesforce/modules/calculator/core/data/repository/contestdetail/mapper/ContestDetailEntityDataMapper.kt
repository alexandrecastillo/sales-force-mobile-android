package biz.belcorp.salesforce.modules.calculator.core.data.repository.contestdetail.mapper

import biz.belcorp.salesforce.core.entities.calculator.ContestDetailEntity
import biz.belcorp.salesforce.modules.calculator.core.domain.entities.ContestDetail

class ContestDetailEntityDataMapper {

    fun parseContestDetail(list: List<ContestDetailEntity>?): List<ContestDetail>? {
        val entity = arrayListOf<ContestDetail>()

        list?.forEach {
            entity.add(ContestDetail(
                it.nivelSE,
                it.descripcionNivel,
                it.variable1,
                it.nivelBT1,
                it.variable2,
                it.nivelBT2,
                it.bono))
        }

        return entity
    }
}
