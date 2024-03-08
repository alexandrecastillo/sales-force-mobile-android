package biz.belcorp.salesforce.modules.inspires.core.data.repository.terms.mapper

import biz.belcorp.salesforce.core.entities.sql.inspires.InspiraCondicionesEntity
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraCondiciones

class TermsEntityDataMapper {

    fun parseLevelParameterList(list: List<InspiraCondicionesEntity>?): List<InspiraCondiciones>? {
        val entity = arrayListOf<InspiraCondiciones>()

        list?.forEach {
            entity.add(InspiraCondiciones(
                it.codigo,
                it.condicion,
                it.descripcion))
        }

        return entity
    }

    fun parseLevelParameter(inspiraCondicionesEntity: InspiraCondicionesEntity?): InspiraCondiciones? {

        return  inspiraCondicionesEntity?.let {
            InspiraCondiciones(
                it.codigo,
                it.condicion,
                it.descripcion)
        }
    }
}
