package biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete

import com.google.gson.annotations.SerializedName

data class SolicitudPostulanteEstadosResponse(
    @SerializedName("SolicitudPostulanteId")
    val solicitudPostulanteId: Int?,
    @SerializedName("TipoDocumento")
    val tipoDocumento: Int?,
    @SerializedName("NumeroDocumento")
    val numeroDocumento: String?,
    @SerializedName("EstadoPostulante")
    val estadoPostulante: Int?,
    @SerializedName("EstadoGeo")
    val estadoGeo: Int?,
    @SerializedName("EstadoBuroCrediticio")
    val estadoBuroCrediticio: Int?,
    @SerializedName("EstadoTelefonico")
    val estadoTelefonico: Int?
)
