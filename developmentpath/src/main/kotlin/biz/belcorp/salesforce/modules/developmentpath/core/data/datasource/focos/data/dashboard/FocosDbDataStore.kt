package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.data.dashboard

import biz.belcorp.salesforce.core.entities.sql.focos.FocoRddEntity
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.select

class FocosDbDataStore {
    fun obtenerModelosFoco(): MutableList<FocoRddEntity> {
        return (select from FocoRddEntity::class).queryList()
    }
}
