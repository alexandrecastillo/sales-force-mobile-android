package biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DevueltoSacRequest {
    @SerialName("SolicitudPostulanteID")
    var solicitudPostulanteId: Int? = null
}
