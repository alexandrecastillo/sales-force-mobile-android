package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersIndicator

data class SaleOrdersHeaderModel(
    val titleGoals: String = EMPTY_STRING,
    val goals: List<ContentModel> = emptyList(),
    val titleAchievements: String = EMPTY_STRING,
    val achievements: List<ContentModel> = emptyList(),
    val model: SaleOrdersIndicator
) {
    val hasGoals get() = goals.isNotEmpty()
    val hasAchievements get() = achievements.isNotEmpty()
}
