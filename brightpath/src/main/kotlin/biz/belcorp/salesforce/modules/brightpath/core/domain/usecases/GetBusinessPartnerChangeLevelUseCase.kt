package biz.belcorp.salesforce.modules.brightpath.core.domain.usecases

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerChangeLevelEntity
import biz.belcorp.salesforce.modules.brightpath.core.data.repository.BusinessPartnerChangeLevelRepository

class GetBusinessPartnerChangeLevelUseCase(private val repository: BusinessPartnerChangeLevelRepository) {

    fun getBusinessPartnerLevel(uaKey: LlaveUA): MutableList<BusinessPartnerChangeLevelEntity> =
        repository.getBusinessPartnerLevelData(uaKey)

}
