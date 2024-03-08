package biz.belcorp.salesforce.modules.calculator.core.data.repository.upperlevel.mapper

import biz.belcorp.salesforce.core.entities.calculator.UpperLevelEntity
import biz.belcorp.salesforce.modules.calculator.core.domain.entities.UpperLevel

class UpperLevelEntityDataMapper {

    fun parseUpperLevel(list: List<UpperLevelEntity>?): List<UpperLevel>? {
        val entity = arrayListOf<UpperLevel>()

        list?.forEach {
            entity.add(UpperLevel(
                it.nivelID,
                it.descripcion))
        }

        return entity
    }
}
