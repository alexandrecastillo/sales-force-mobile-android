package biz.belcorp.salesforce.modules.brightpath.features.header

interface BrightPathHeaderKpiView {
    fun showHeader(ordersGoalValue: String)
    fun showTitle(currentCampaign: String)

    fun showPrevCampaignText(totalPrevCampaign: String)
    fun showPrevCampaignValues(values: Array<String>)

    fun showCurrentPeriodText(currentPeriod: String)
    fun showCurrentPeriodValues(accumulatedPrevPeriodArgs: Array<String>)
}
