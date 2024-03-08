package biz.belcorp.salesforce.core.data.repository.consultants.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox
import biz.belcorp.salesforce.core.entities.ConsultantEntity
import biz.belcorp.salesforce.core.entities.ConsultantEntity_
import io.objectbox.kotlin.boxFor
import io.objectbox.kotlin.equal

class ConsultantsDataStore {

    fun get(id: Int): ConsultantEntity? {
        return ObjectBox.boxStore.boxFor<ConsultantEntity>().query()
            .equal(ConsultantEntity_.consultantId, id)
            .build()
            .findFirst()
    }

}
