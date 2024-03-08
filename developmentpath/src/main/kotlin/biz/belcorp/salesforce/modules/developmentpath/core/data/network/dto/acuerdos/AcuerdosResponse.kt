package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.acuerdos

import biz.belcorp.salesforce.core.data.network.dto.BaseResponse
import biz.belcorp.salesforce.core.entities.sql.datos.AcuerdoEntity

data class AcuerdosResponse(
    val resultado: List<AcuerdoEntity>
) : BaseResponse()
