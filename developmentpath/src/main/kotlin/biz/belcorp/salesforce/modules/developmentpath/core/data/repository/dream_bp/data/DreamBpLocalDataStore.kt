package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox
import biz.belcorp.salesforce.core.entities.dreambp.DreamBpEntity
import biz.belcorp.salesforce.core.entities.dreambp.DreamBpEntity_
import biz.belcorp.salesforce.core.utils.deleteAndInsert
import biz.belcorp.salesforce.core.utils.equal
import io.objectbox.kotlin.boxFor

class DreamBpLocalDataStore {

    fun save(items: List<DreamBpEntity>) {
        ObjectBox.boxStore.boxFor<DreamBpEntity>().deleteAndInsert(items)
    }

    fun getDreamsByBusinessPartnerId(businessPartnerId: String?): List<DreamBpEntity?> =
        ObjectBox.boxStore.boxFor<DreamBpEntity>().query()
            .equal(DreamBpEntity_.bpCode, businessPartnerId!!)
            .build()
            .find()

    fun deleteDream(id: Long) {
        ObjectBox.boxStore.boxFor<DreamBpEntity>().remove(id)
    }

    fun findDreamById(id: String): DreamBpEntity? =
        ObjectBox.boxStore.boxFor<DreamBpEntity>().query()
            .equal(DreamBpEntity_.dreamId, id)
            .build()
            .findFirst()
}
