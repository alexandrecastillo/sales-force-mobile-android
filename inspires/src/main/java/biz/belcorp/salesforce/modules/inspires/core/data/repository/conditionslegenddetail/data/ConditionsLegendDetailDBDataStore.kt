package biz.belcorp.salesforce.modules.inspires.core.data.repository.conditionslegenddetail.data

import biz.belcorp.salesforce.core.entities.sql.inspires.InspiraCondicionesLeyendaDetalleEntity
import biz.belcorp.salesforce.core.utils.doOnSingle
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Single

class ConditionsLegendDetailDBDataStore {

    fun all(): Single<List<InspiraCondicionesLeyendaDetalleEntity>> {

        return doOnSingle {
            Select().from(InspiraCondicionesLeyendaDetalleEntity::class.java)
                .queryList()
        }
    }

}
