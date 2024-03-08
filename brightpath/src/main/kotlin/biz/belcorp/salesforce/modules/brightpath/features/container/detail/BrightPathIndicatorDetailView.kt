package biz.belcorp.salesforce.modules.brightpath.features.container.detail

interface BrightPathIndicatorDetailView {
    fun showHeader()
    fun showCampaign(campaignText: String)
    fun showConsultancyFilter()
    fun showBeautyConsultantListView()
    fun showUaSegment()
    fun getBeautyConsultantList(uaSegmentSelected: String, constancySelected: String, typeSelection: Int)
    fun showEndPeriodTitle(title: String)
}
