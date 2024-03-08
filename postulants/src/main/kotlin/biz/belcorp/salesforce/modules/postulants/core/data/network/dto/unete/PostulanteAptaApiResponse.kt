package biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete

import biz.belcorp.salesforce.core.data.network.dto.BaseResponse
import com.google.gson.annotations.SerializedName

class PostulanteAptaApiResponse : BaseResponse() {

    @SerializedName("resultado")
    val resultado: PostulanteApta? = null

    class PostulanteApta(
            @SerializedName("codigo")
            val codigo: String? = null,
            @SerializedName("esApta")
            val esApta: Boolean = false,
            @SerializedName("idEstadoActividad")
            val idEstadoActividad: Int? = null,
            @SerializedName("mensajeError")
            val mensajeError: String? = null,
            @SerializedName("nombreCompleto")
            val nombreCompleto: String? = null,
            @SerializedName("tipoError")
            val tipoError: Int? = null,
            @SerializedName("tipoSolicitud")
            val tipoSolicitud: String? = null
    )
}
