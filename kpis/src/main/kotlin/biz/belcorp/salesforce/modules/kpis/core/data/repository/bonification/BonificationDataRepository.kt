package biz.belcorp.salesforce.modules.kpis.core.data.repository.bonification

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.usecases.socialbonus.SocialBonusConstant.HIGH_VALUE_BONUS_CODE
import biz.belcorp.salesforce.core.domain.usecases.socialbonus.SocialBonusConstant.LOW_VALUE_BONUS_CODE
import biz.belcorp.salesforce.core.utils.zeroIfNull
import biz.belcorp.salesforce.modules.kpis.core.data.repository.bonification.cloud.BonificationCloudStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.bonification.cloud.dto.BonusParams
import biz.belcorp.salesforce.modules.kpis.core.data.repository.bonification.cloud.dto.BonusQuery
import biz.belcorp.salesforce.modules.kpis.core.data.repository.bonification.data.BonificationDataStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.bonification.mapper.BonificationMapper
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiQueryParams
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.newcycle.BonusInfo
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.BonificationRepository

class BonificationDataRepository(
    private val cloudStore: BonificationCloudStore,
    private val dataStore: BonificationDataStore,
    private val mapper: BonificationMapper
) : BonificationRepository {

    override suspend fun sync(request: KpiQueryParams) {
        val params = request.run { BonusParams(country, campaign.first(), region, zone) }
        val query = BonusQuery(params)
        val bonification = cloudStore.getBonificationInfo(query)
        val entities = mapper.map(bonification)
        dataStore.saveBonification(entities)
    }

    override suspend fun getBonusInfo(uaKey: LlaveUA, campaign: String): BonusInfo {
        val bonification = dataStore.getBonification(uaKey, campaign)
        val lowValueBonus = bonification.find { it.code == LOW_VALUE_BONUS_CODE }?.amount
        val highValueBonus = bonification.find { it.code == HIGH_VALUE_BONUS_CODE }?.amount
        return BonusInfo(lowValueBonus.zeroIfNull(), highValueBonus.zeroIfNull())
    }
}
