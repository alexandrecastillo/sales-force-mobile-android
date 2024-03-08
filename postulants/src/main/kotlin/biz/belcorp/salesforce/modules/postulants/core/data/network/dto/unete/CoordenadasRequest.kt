package biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete

import com.google.gson.annotations.SerializedName

data class CoordenadasRequest(
    @SerializedName("direccion")
    val direccion: String? = null,
    @SerializedName("pais")
    val pais: String? = null,
    @SerializedName("ciudad")
    val ciudad: String? = null,
    @SerializedName("area")
    val area: String? = null
)
