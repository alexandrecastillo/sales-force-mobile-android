package biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete

import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.Coordenadas
import com.google.gson.annotations.SerializedName

data class CoordendasResponse(
    @SerializedName("Latitud")
    val latitud: String? = null,
    @SerializedName("Longitud")
    val longitud: String? = null
)

fun CoordendasResponse.toModel(): Coordenadas = Coordenadas(
    latitud.orEmpty(), longitud.orEmpty()
)
