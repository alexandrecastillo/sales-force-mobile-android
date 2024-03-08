package biz.belcorp.salesforce.modules.inspires.features.travel

interface InspireTravelView {
    fun showHeaderCard(active: Boolean, limited: Boolean)
    fun createTabs(active: Boolean, limited: Boolean)
}
