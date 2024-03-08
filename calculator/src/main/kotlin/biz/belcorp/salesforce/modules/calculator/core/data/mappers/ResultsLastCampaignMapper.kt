package biz.belcorp.salesforce.modules.calculator.core.data.mappers

import biz.belcorp.salesforce.core.utils.zeroIfNull
import biz.belcorp.salesforce.modules.calculator.core.data.entities.ResultsCampaignData
import biz.belcorp.salesforce.modules.calculator.core.domain.entities.ResultCampaign
import biz.belcorp.salesforce.modules.calculator.core.domain.entities.ResultsOptional

class ResultsLastCampaignMapper {
    fun map(entity: ResultsCampaignData): ResultsOptional {
        return ResultsOptional(mapEntity(entity))
    }

    private fun mapEntity(entity: ResultsCampaignData): ResultCampaign {
        return ResultCampaign(
            campaign = entity.saleOrders?.campaign.orEmpty(),
            region = entity.saleOrders?.region.orEmpty(),
            zone = entity.saleOrders?.zone.orEmpty(),
            section = entity.saleOrders?.section.orEmpty(),
            catalogSale = entity.saleOrders?.catalogSale,
            catalogSaleGoal = entity.saleOrders?.catalogSaleGoal,
            orders = entity.saleOrders?.orders,
            ordersGoal = entity.saleOrders?.ordersGoal,
            capitalization = entity.capitalization?.capitalizationReal,
            capitalizationGoal = entity.capitalization?.capitalizationGoal,
            entries = entity.capitalization?.capitalizationEntries,
            entriesGoal = entity.capitalization?.capitalizationEntriesGoal,
            activesRetention = entity.saleOrders?.activesRetentionPercentage,
            activity = entity.saleOrders?.activityPercentage,
            consultants6d6 = entity.newCycle?.lowValueOrders6d6.zeroIfNull() + entity.newCycle?.highValueOrders6d6.zeroIfNull(),
            consultantsWithDebt = entity.collection?.debtorConsultants,
            expenses = entity.capitalization?.capitalizationExpenses,
            possibleExpenses = entity.capitalization?.pegsReal,
            recovery = entity.collection?.percentage,
            reentries = entity.capitalization?.capitalizationReentries
        )
    }
}

