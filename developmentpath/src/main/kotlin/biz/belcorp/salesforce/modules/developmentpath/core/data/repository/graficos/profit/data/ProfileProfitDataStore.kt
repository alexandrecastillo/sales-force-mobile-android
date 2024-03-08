package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.profit.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.collection.ProfitEntity
import biz.belcorp.salesforce.core.entities.collection.ProfitEntity_
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.equal
import io.objectbox.kotlin.boxFor

class ProfileProfitDataStore {

    fun save(items: List<ProfitEntity>) {
        boxStore.boxFor<ProfitEntity>().put(items)
    }

    fun getDataByUaAndCampaign(
        uaKey: LlaveUA,
        campaigns: List<String>
    ): List<ProfitEntity> {
        val list = getDataByUa(uaKey)
        checkToRemove(list, campaigns.first())
        return list.filter { it.campaign in campaigns }
    }

    private fun getDataByUa(uaKey: LlaveUA): List<ProfitEntity> =
        boxStore.boxFor<ProfitEntity>().query()
            .equal(ProfitEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
            .and()
            .equal(ProfitEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
            .build()
            .find()

    private fun checkToRemove(items: List<ProfitEntity>, campaign: String) {
        boxStore.runInTx {
            val itemsToRemove = items.filter { it.campaign.toInt() < campaign.toInt() }
            if (itemsToRemove.isNotEmpty()) {
                boxStore.boxFor<ProfitEntity>().remove(itemsToRemove)
            }
        }
    }

}
