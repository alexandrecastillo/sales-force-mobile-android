package biz.belcorp.salesforce.modules.inspires.core.data.repository.advances.data

import biz.belcorp.salesforce.core.entities.sql.inspires.InspiraAvancesEntity
import biz.belcorp.salesforce.core.utils.doOnSingle
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Single

class AdvancesDBDataStore {

    fun all(): Single<List<InspiraAvancesEntity>> {

        return doOnSingle {
            Select().from(InspiraAvancesEntity::class.java)
                .queryList()
        }
    }

}
