package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.entities.dream.DreamEntity
import biz.belcorp.salesforce.core.entities.dream.DreamEntity_
import biz.belcorp.salesforce.core.utils.deleteAndInsert
import biz.belcorp.salesforce.core.utils.equal
import biz.belcorp.salesforce.modules.developmentpath.utils.Constants
import io.objectbox.kotlin.boxFor

class DreamLocalDataStore {

    fun save(items: List<DreamEntity>) {
        boxStore.boxFor<DreamEntity>().deleteAndInsert(items)
    }

    fun getDreamsByConsultantId(consultantId: String?): List<DreamEntity?> =
        boxStore.boxFor<DreamEntity>().query()
            .equal(DreamEntity_.consultantCode, consultantId!!)
            .build()
            .find()

    fun deleteDream(id: Long) {
        boxStore.boxFor<DreamEntity>().remove(id)
    }

    fun findDreamById(id: String): DreamEntity? = boxStore.boxFor<DreamEntity>().query()
        .equal(DreamEntity_.dreamId, id)
        .build()
        .findFirst()
}
