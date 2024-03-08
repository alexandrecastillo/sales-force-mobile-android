package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.info.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerEntity
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerEntity_
import biz.belcorp.salesforce.core.utils.equal
import io.objectbox.kotlin.boxFor


class BusinessPartnerInfoDataStore {

    fun getBusinessPartner(code: String): BusinessPartnerEntity? {
        return ObjectBox.boxStore.boxFor<BusinessPartnerEntity>().query()
            .equal(BusinessPartnerEntity_.partnerCode, code)
            .build()
            .findFirst()
    }

}
