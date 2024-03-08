package biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete

import biz.belcorp.salesforce.modules.postulants.utils.Constant
import com.google.gson.annotations.SerializedName

data class ResultadoAPI<out T>(val name: String = Constant.EMPTY_STRING) {

    @SerializedName("codigo")
    val codigo: String? = null
    @SerializedName("mensaje")
    val mensaje: String? = null
    @SerializedName("respuesta")
    val respuesta: T? = null

}
