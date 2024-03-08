package biz.belcorp.salesforce.modules.inspires.core.data.repository.terms.data

import biz.belcorp.salesforce.core.entities.sql.inspires.InspiraCondicionesEntity
import biz.belcorp.salesforce.core.utils.doOnSingle
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Single

class TermsDBDataStore {

    fun all(): Single<List<InspiraCondicionesEntity>> {

        return doOnSingle {
            Select().from(InspiraCondicionesEntity::class.java)
                .queryList()
        }
    }

}
