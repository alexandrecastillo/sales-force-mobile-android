package biz.belcorp.salesforce.modules.consultants.core.domain.repository.mypartners

import biz.belcorp.salesforce.modules.consultants.core.data.network.dto.MyPartnersParams


interface MyPartnersSyncRepository {

    suspend fun sync(params: MyPartnersParams)
}
