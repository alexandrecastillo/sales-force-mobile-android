package biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PostulanteProactivoRequest {
    @SerialName("codigoIso")
    var codigoIso: String? = null

    @SerialName("solicitudPostulanteId")
    var solicitudPostulanteId: String? = null

    @SerialName("estado")
    var estado: Int? = null

    @SerialName("motivoRechazo")
    var motivoRechazo: String? = null

}
