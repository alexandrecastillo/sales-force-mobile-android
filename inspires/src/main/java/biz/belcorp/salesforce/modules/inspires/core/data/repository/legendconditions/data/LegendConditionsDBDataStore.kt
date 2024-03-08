package biz.belcorp.salesforce.modules.inspires.core.data.repository.legendconditions.data

import biz.belcorp.salesforce.core.entities.sql.inspires.InspiraCondicionesLeyendaEntity
import biz.belcorp.salesforce.core.utils.doOnSingle
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Single

class LegendConditionsDBDataStore {

    fun all(): Single<List<InspiraCondicionesLeyendaEntity>> {

        return doOnSingle {
            Select().from(InspiraCondicionesLeyendaEntity::class.java)
                .queryList()
        }
    }

}
