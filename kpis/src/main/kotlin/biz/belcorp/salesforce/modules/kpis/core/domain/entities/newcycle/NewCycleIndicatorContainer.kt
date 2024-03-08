package biz.belcorp.salesforce.modules.kpis.core.domain.entities.newcycle

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiData

data class NewCycleIndicatorContainer(
    var listIndicators: KpiData<NewCycleIndicator>,
    val currencySymbol: String,
    val isBilling: Boolean,
    val role: Rol,
    val currentCampaignNameOnly: String = EMPTY_STRING,
    val previousCampaignNameOnly: String = EMPTY_STRING,
    val bonusInfo: BonusInfo,
    val isThirdPerson: Boolean
)
