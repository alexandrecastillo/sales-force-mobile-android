package biz.belcorp.salesforce.modules.calculator.core.data.repository.upperlevel.data

import biz.belcorp.salesforce.core.entities.calculator.UpperLevelEntity
import com.raizlabs.android.dbflow.sql.language.Select

class UpperLevelDBDataStore {

    fun all(): List<UpperLevelEntity>? {
        return requireNotNull(Select().from(UpperLevelEntity::class.java)
            .queryList())
    }

}
