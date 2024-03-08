package biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete

import biz.belcorp.salesforce.modules.postulants.utils.Constant
import com.google.gson.annotations.SerializedName

data class PrePostulantesContenidoResponse(val name: String = Constant.EMPTY_STRING) {

    @SerializedName("mensaje")
    var mensaje: String? = null

    @SerializedName("codigo")
    var codigo: String? = null

    @SerializedName("respuesta")
    var resultado: List<PrePostulanteDTO>? = null

}
