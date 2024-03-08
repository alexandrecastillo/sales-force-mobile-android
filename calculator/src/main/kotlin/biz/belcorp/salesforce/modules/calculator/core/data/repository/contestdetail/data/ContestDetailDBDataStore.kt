package biz.belcorp.salesforce.modules.calculator.core.data.repository.contestdetail.data

import biz.belcorp.salesforce.core.entities.calculator.ContestDetailEntity
import biz.belcorp.salesforce.core.utils.doOnSingle
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Single

class ContestDetailDBDataStore {

    fun all(): Single<List<ContestDetailEntity>> {

        return doOnSingle {
            Select().from(ContestDetailEntity::class.java)
                .queryList()
        }
    }
}
