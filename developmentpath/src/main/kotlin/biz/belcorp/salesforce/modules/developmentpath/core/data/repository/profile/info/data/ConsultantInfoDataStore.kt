package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.info.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.entities.ConsultantEntity
import biz.belcorp.salesforce.core.entities.ConsultantEntity_
import biz.belcorp.salesforce.core.utils.equal
import io.objectbox.kotlin.boxFor


class ConsultantInfoDataStore {

    fun getConsultant(code: String): ConsultantEntity? {
        return boxStore.boxFor<ConsultantEntity>().query()
            .equal(ConsultantEntity_.code, code)
            .build()
            .findFirst()
    }

}
