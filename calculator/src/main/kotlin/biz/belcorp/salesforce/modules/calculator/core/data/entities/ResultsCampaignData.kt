package biz.belcorp.salesforce.modules.calculator.core.data.entities

import biz.belcorp.salesforce.core.entities.capitalization.CapitalizationEntity
import biz.belcorp.salesforce.core.entities.collection.CollectionEntity
import biz.belcorp.salesforce.core.entities.newcycles.NewCycleEntity
import biz.belcorp.salesforce.core.entities.saleorders.SaleOrdersEntity

class ResultsCampaignData (
    val saleOrders: SaleOrdersEntity?,
    val capitalization: CapitalizationEntity?,
    val newCycle: NewCycleEntity?,
    val collection: CollectionEntity?
)
