package biz.belcorp.salesforce.modules.inspires.core.data.repository.progressperiod.data

import biz.belcorp.salesforce.core.entities.sql.inspires.InspiraAvancesPeriodoEntity
import biz.belcorp.salesforce.core.utils.doOnSingle
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Single

class ProgressPeriodDBDataStore {

    fun all(): Single<List<InspiraAvancesPeriodoEntity>> {

        return doOnSingle {
            Select().from(InspiraAvancesPeriodoEntity::class.java)
                .queryList()
        }
    }

    fun has(): Single<Boolean> {

        return doOnSingle {
            Select().from(InspiraAvancesPeriodoEntity::class.java)
                .hasData()
        }
    }

}
