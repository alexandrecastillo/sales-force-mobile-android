package biz.belcorp.salesforce.modules.brightpath.features.container


interface IndicatorDetailView {

    fun addConsultantsDrillDown()
    fun drawHeaderDetailKpiView()
    fun showCampaign(campaignText: String)
    fun showLoading()
    fun hideLoading()
    fun showErrorMessaje()

}
