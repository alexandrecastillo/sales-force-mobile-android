package biz.belcorp.salesforce.modules.consultants.core.domain.usecases.mypartners

import biz.belcorp.salesforce.core.entities.mypartners.MyPartnerEntity
import biz.belcorp.salesforce.modules.consultants.core.data.datasource.MyPartnersDataStore

class MyPartnersStoreUseCase(
    private val dataStore: MyPartnersDataStore
) {

    fun getMyPartners(): List<MyPartnerEntity> {

        return dataStore.getMyPartners()

    }


}
