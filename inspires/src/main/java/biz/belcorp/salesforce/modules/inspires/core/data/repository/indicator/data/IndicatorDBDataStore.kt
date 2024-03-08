package biz.belcorp.salesforce.modules.inspires.core.data.repository.indicator.data

import biz.belcorp.salesforce.core.entities.sql.inspires.InspiraIndicadorEntity
import biz.belcorp.salesforce.core.utils.doOnSingle
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Single

class IndicatorDBDataStore {

    fun one(): Single<InspiraIndicadorEntity> {

        return doOnSingle {
            Select().from(InspiraIndicadorEntity::class.java)
                .querySingle()!!
        }
    }

}
