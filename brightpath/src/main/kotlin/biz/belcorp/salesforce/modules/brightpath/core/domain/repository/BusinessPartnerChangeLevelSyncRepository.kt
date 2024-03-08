package biz.belcorp.salesforce.modules.brightpath.core.domain.repository

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerChangeLevelEntity
import biz.belcorp.salesforce.modules.brightpath.core.data.network.dto.BusinessPartnerChangeLevelParams

interface BusinessPartnerChangeLevelSyncRepository {

    suspend fun sync(params: BusinessPartnerChangeLevelParams)

    fun getBusinessPartnerLevelData(uaKey: LlaveUA): MutableList<BusinessPartnerChangeLevelEntity>

}
