package biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete

import biz.belcorp.salesforce.modules.postulants.utils.Constant
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class PostulantesRequest(val name: String = Constant.EMPTY_STRING) {
    @SerializedName("Pais")
    var pais: String? = null
    @SerializedName("Region")
    var region: String? = null
    @SerializedName("Zona")
    var zona: String? = null
    @SerializedName("Seccion")
    var seccion: String? = null
    @SerializedName("Rol")
    var rol: String? = null
    @SerializedName("tipoFiltro")
    var tipoFiltro: Int = 0
    @SerializedName("textoBusqueda")
    var textoBusqueda: String? = null
}
