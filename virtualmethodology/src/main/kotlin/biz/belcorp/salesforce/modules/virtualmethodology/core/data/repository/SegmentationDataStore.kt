package biz.belcorp.salesforce.modules.virtualmethodology.core.data.repository

import biz.belcorp.salesforce.core.entities.sql.metodologia.SegmentacionEntity
import biz.belcorp.salesforce.core.utils.deleteAndInsert
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.select

class SegmentationDataStore {

    fun getSegmentation(): List<SegmentacionEntity> {
        val where = (select from SegmentacionEntity::class)
        return where.queryList()
    }

    fun save(list: List<SegmentacionEntity>) {
        list.deleteAndInsert()
    }

}
