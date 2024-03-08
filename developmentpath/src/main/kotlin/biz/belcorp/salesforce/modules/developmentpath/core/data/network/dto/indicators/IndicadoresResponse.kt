package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.indicators

import biz.belcorp.salesforce.core.data.network.dto.BaseResponse
import biz.belcorp.salesforce.core.entities.sql.indicators.IndicadorVisitasRDDEntity

data class IndicadoresResponse(val resultado: List<IndicadorVisitasRDDEntity>) : BaseResponse()
