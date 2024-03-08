package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.model

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA

class DigitalSaleQueryParams(
    var country: String,
    var campaigns: List<String>,
    var consultantCode: String,
    var uakey: LlaveUA
)