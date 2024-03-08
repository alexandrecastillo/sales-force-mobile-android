package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ranking.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.ranking.RankingEntity
import biz.belcorp.salesforce.core.entities.ranking.RankingEntity_
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.equal
import io.objectbox.kotlin.boxFor

class ProfileRankingDataStore {

    fun save(items: List<RankingEntity>) {
        boxStore.boxFor<RankingEntity>().put(items)
    }

    private fun getDataByUa(uaKey: LlaveUA): List<RankingEntity> =
        boxStore.boxFor<RankingEntity>().query()
            .equal(RankingEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
            .and()
            .equal(RankingEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
            .build()
            .find()

    fun getDataByUaAndCampaign(
        uaKey: LlaveUA,
        campaigns: List<String>
    ): List<RankingEntity> {
        val list = getDataByUa(uaKey)
        checkToRemove(list, campaigns.first())
        return list.filter { it.campaign in campaigns }
    }

    private fun checkToRemove(items: List<RankingEntity>, campaign: String) {
        boxStore.runInTx {
            val itemsToRemove = items.filter { it.campaign.toInt() < campaign.toInt() }
            if (itemsToRemove.isNotEmpty()) {
                boxStore.boxFor<RankingEntity>().remove(itemsToRemove)
            }
        }
    }

}
