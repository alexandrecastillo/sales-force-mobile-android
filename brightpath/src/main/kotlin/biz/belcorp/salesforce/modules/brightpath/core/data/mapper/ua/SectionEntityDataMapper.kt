package biz.belcorp.salesforce.modules.brightpath.core.data.mapper.ua

import biz.belcorp.salesforce.core.domain.entities.ua.Seccion
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerEntity

class SectionEntityDataMapper {

    fun map(list: List<BusinessPartnerEntity>): List<Seccion> {
        return list.map { map(it) }
    }

    private fun map(entity: BusinessPartnerEntity) = Seccion().apply {
        codigo = entity.section
        nivel = entity.level
        sociaEmpresaria = entity.name
    }
}
