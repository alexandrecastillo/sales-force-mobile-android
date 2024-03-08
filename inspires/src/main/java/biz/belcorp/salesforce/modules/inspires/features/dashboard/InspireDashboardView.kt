package biz.belcorp.salesforce.modules.inspires.features.dashboard

import biz.belcorp.salesforce.modules.inspires.model.InspireIndicatorModel

interface InspireDashboardView {
    fun showDestinationImage(country: String)
    fun showValues(model: InspireIndicatorModel)
    fun setListeners()
    fun showCampaign(campaign: String)
    fun showError()
}
