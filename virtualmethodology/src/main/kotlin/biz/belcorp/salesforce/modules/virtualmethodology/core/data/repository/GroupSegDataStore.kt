package biz.belcorp.salesforce.modules.virtualmethodology.core.data.repository

import biz.belcorp.salesforce.core.entities.sql.metodologia.GrupoSegEntity
import biz.belcorp.salesforce.core.utils.deleteAndInsert
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.select

class GroupSegDataStore {

    fun getGroupSeg(): List<GrupoSegEntity> {
        val where = (select from GrupoSegEntity::class)
        return where.queryList()
    }

    fun save(list: List<GrupoSegEntity>) {
        list.deleteAndInsert()
    }

}
