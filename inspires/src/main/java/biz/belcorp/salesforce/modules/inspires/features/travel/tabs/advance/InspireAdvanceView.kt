package biz.belcorp.salesforce.modules.inspires.features.travel.tabs.advance

import biz.belcorp.salesforce.modules.inspires.model.InspiraAvancePeriodoModel
import biz.belcorp.salesforce.modules.inspires.model.InspiraAvancesModel

interface InspireAdvanceView {
    fun showHeaderIcon(active: Boolean)
    fun showHeaderMessage(active: Boolean, name: String?)
    fun showTitle(campaign: String, active: Boolean)
    fun showList(list: List<InspiraAvancesModel>, limited: Boolean, active: Boolean, hasPeriod: Boolean)
    fun showPeriodList(list: List<InspiraAvancePeriodoModel>)
    fun hideContent()
}
