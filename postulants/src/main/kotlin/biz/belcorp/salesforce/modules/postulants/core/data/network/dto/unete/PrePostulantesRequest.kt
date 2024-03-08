package biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete

import biz.belcorp.salesforce.modules.postulants.utils.Constant
import com.google.gson.annotations.SerializedName

data class PrePostulantesRequest(val name: String = Constant.EMPTY_STRING) {

    @SerializedName("Pais")
    var pais: String? = null

    @SerializedName("Zona")
    var zona: String? = null

    @SerializedName("Seccion")
    var seccion: String? = null

    @SerializedName("tipoFiltro")
    var tipoFiltro: Int = Constant.NUMERO_CERO

    @SerializedName("textoBusqueda")
    var textoBusqueda: String? = null

}
