package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.performance.mapper

import biz.belcorp.salesforce.core.entities.SuccessfuHistoricEntity
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.profile.ProfilePerformanceSe
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.profile.SuccessfulHistoricItem
import biz.belcorp.salesforce.modules.developmentpath.utils.maskCampaignWithPrefix

class PerformanceMapper {

    fun map(entity: BusinessPartnerEntity): ProfilePerformanceSe {
        return ProfilePerformanceSe(
            productivity = entity.productivity,
            successfulHistoric = map(entity.successfuHistoric)
        )
    }

    fun map(list: List<SuccessfuHistoricEntity>): List<SuccessfulHistoricItem> {
        return list.map { map(it) }
    }

    fun map(entity: SuccessfuHistoricEntity): SuccessfulHistoricItem {
        return SuccessfulHistoricItem(
            campaign = entity.campaign.maskCampaignWithPrefix(),
            isSuccessful = entity.value
        )
    }

}
