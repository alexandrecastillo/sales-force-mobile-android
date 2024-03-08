package biz.belcorp.salesforce.modules.brightpath.features.ua

interface UASegmentView {
    fun showUserSegment(countryInt: Int)
    fun setUpRecyclerView()
    fun showUaItemAsSelected(pos: Int)
}
