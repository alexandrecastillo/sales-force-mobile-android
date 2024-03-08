package biz.belcorp.salesforce.core.domain.entities.options

class Option(
    var code: Long,
    val description: String? = null,
    val position: Int = 0,
    val url: String? = null
)
