package biz.belcorp.salesforce.core.domain.entities.consultants

data class ConsultantConfiguration(
    val isPdv: Boolean,
    val currency: String,
    val storeTitle: String,
    val flagMto: Int
)
