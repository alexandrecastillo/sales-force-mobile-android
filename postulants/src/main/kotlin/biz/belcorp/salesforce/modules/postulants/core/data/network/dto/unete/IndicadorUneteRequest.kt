package biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
class IndicadorUneteRequest {
    @SerialName("pais")
    var pais: String? = null
    @SerialName("zona")
    var zona: String? = null
    @SerialName("region")
    var region: String? = null
    @SerialName("rol")
    var rol: String? = null
    @SerialName("seccion")
    var seccion: String? = null
}
