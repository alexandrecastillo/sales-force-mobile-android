package biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA

open class Filter(
    val uaKey: LlaveUA,
    val filterables: List<Filterable>
)
