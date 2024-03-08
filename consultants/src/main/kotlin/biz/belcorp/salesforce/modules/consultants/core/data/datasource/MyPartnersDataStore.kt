package biz.belcorp.salesforce.modules.consultants.core.data.datasource

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox
import biz.belcorp.salesforce.core.entities.mypartners.MyPartnerEntity
import biz.belcorp.salesforce.core.utils.deleteAndInsert
import io.objectbox.kotlin.boxFor

class MyPartnersDataStore {

    fun saveChangeLevel(level: List<MyPartnerEntity>) {
        ObjectBox.boxStore.boxFor<MyPartnerEntity>().deleteAndInsert(level)
    }

    fun getMyPartners(
    ): List<MyPartnerEntity> {
        return ObjectBox.boxStore.boxFor<MyPartnerEntity>()
            .query()
            .build()
            .find()
    }

}
