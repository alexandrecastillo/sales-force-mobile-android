package biz.belcorp.salesforce.modules.developmentmaterial.core.data.repository

import biz.belcorp.salesforce.core.entities.sql.materialdesarrollo.MaterialDesarrolloEntity
import biz.belcorp.salesforce.core.utils.deleteAndInsert
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.select


class MaterialesDesarrolloDataStore {

    fun getDocuments(): List<MaterialDesarrolloEntity> {
        val where = (select from MaterialDesarrolloEntity::class)
        return where.queryList()
    }

    fun saveDocuments(list: List<MaterialDesarrolloEntity>) {
        list.deleteAndInsert()
    }

}
