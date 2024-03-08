package biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete

import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.CoordenadasRequest

data class ParamGetCoordenadas(
    var direccion: String? = null,
    var pais: String? = null,
    var ciudad: String? = null,
    var area: String? = null
)

fun ParamGetCoordenadas.toRequest(): CoordenadasRequest = CoordenadasRequest(
    direccion, pais, ciudad, area
)
