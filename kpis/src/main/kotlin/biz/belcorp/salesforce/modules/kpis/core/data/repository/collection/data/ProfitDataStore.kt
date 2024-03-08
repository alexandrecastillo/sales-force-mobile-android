package biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.collection.ProfitEntity
import biz.belcorp.salesforce.core.entities.collection.ProfitEntity_
import biz.belcorp.salesforce.core.entities.collection.ProfitOrderEntity
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.equal
import biz.belcorp.salesforce.core.utils.inValues
import io.objectbox.kotlin.boxFor

class ProfitDataStore {

    fun saveProfit(profit: List<ProfitEntity>) {
        with(boxStore) {
            runInTx {
                boxFor<ProfitOrderEntity>().removeAll()

                boxFor<ProfitEntity>().query()
                    .equal(ProfitEntity_.region, profit[0].region).and()
                    .equal(ProfitEntity_.zone, profit[0].zone).and()
                    .equal(ProfitEntity_.section, profit[0].section)
                    .build()
                    .remove()

                boxFor<ProfitEntity>().put(profit)
            }
        }
    }

    fun getProfitByCampaigns(uaKey: LlaveUA, campaigns: List<String>): List<ProfitEntity> {
        return boxStore.boxFor<ProfitEntity>().query()
            .equal(ProfitEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
            .and()
            .equal(ProfitEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
            .and()
            .equal(ProfitEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            .and()
            .inValues(ProfitEntity_.campaign, campaigns.toTypedArray())
            .build()
            .find()
    }
}
