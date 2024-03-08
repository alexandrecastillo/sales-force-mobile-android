package biz.belcorp.salesforce.modules.kpis.core.domain.entities

class KpiQueryParams(
    var country: String,
    var campaign: List<String>,
    var profile: String,
    var region: String,
    var zone: String,
    var section: String,
    var showRegions: Boolean = false,
    var showZones: Boolean = false,
    var showSection: Boolean = false,
    var days: List<String> = emptyList()
) {

    companion object {

        const val COLLECTION_DAYS_21 = "21"
        const val COLLECTION_DAYS_31 = "31"

    }

}
