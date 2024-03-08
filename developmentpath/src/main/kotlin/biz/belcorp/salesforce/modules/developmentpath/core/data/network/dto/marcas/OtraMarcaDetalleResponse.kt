package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.marcas

import biz.belcorp.salesforce.core.data.network.dto.BaseResponse
import biz.belcorp.salesforce.core.entities.sql.marcas.OtraMarcaDetalleEntity

class OtraMarcaDetalleResponse(val resultado: List<OtraMarcaDetalleEntity>) : BaseResponse()
