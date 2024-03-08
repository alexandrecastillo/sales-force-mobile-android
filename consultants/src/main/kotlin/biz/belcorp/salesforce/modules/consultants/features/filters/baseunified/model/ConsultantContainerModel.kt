package biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model


data class ConsultantContainerModel(
    val consultants: List<ConsultantModel>,
    val showSearchBar: Boolean,
    val showFilter: Boolean,
    val showOrder: Boolean,
    val showSummary: Boolean,
    val flagMto: Int
) {
    val hasConsultants get() = consultants.isNotEmpty()
    val totalConsultants get() = consultants.size
}
