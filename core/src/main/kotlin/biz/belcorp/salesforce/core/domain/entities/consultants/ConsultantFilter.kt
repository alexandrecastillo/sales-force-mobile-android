package biz.belcorp.salesforce.core.domain.entities.consultants

import biz.belcorp.salesforce.core.constants.SourceType
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA

data class ConsultantFilter(
    @SourceType val type: Int,
    val uaKey: LlaveUA,
    val filters: List<String> = emptyList()
)
