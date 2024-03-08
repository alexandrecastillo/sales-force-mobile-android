package biz.belcorp.salesforce.modules.consultants.core.data.repository.mypartner

import biz.belcorp.salesforce.modules.consultants.core.data.datasource.MyPartnerMapper
import biz.belcorp.salesforce.modules.consultants.core.data.datasource.MyPartnersDataStore
import biz.belcorp.salesforce.modules.consultants.core.data.network.dto.MyPartnerQuery
import biz.belcorp.salesforce.modules.consultants.core.data.network.dto.MyPartnersParams
import biz.belcorp.salesforce.modules.consultants.core.data.repository.mypartner.cloud.MyPartnerCloudStore
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.mypartners.MyPartnersSyncRepository


class MyPartnerRepository(
    private val cloudStore: MyPartnerCloudStore,
    private val dataStore: MyPartnersDataStore,
    private val mapper: MyPartnerMapper
) : MyPartnersSyncRepository {

    override suspend fun sync(params: MyPartnersParams) {


        val response = cloudStore.getMyPartners(MyPartnerQuery(params))

        val entities = mapper.map(response)

        dataStore.saveChangeLevel(entities)


    }


}
