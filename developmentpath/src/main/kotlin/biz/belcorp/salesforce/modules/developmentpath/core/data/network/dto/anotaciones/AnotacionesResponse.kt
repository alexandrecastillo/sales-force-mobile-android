package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.anotaciones

import biz.belcorp.salesforce.core.data.network.dto.BaseResponse
import biz.belcorp.salesforce.core.entities.sql.anotaciones.AnotacionEntity

data class AnotacionesResponse(
    val resultado: List<AnotacionEntity>) : BaseResponse()
