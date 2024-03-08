package biz.belcorp.salesforce.modules.consultants.core.domain.entities

class FilterSearchConsultant(
    val code: String? = null,
    val name: String? = null,
    val document: String? = null,
    val section: String? = null,
    val status: Int? = null,
    val segment: String? = null,
    val requested: Int? = null,
    val authorized: Int? = null,
    val residue: Int? = null,
    val type: Int? = null,
    val level: String? = null,
    val limit: Int = 0,
    val offset: Int = 0
)
