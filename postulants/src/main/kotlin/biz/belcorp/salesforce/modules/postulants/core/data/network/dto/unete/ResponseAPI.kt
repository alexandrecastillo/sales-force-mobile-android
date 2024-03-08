package biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete

import biz.belcorp.salesforce.modules.postulants.utils.Constant
import com.google.gson.annotations.SerializedName

data class ResponseAPI<T>(val name: String = Constant.EMPTY_STRING) {

    @SerializedName("id")
    var identifier: String? = null
    @SerializedName("fechaRequest")
    var fechaRequest: String? = null
    @SerializedName("httpStatus")
    var httpStatus: Int? = null
    @SerializedName("resultado")
    var resultado: ResultadoAPI<T>? = null
    @SerializedName("fechaResponse")
    var fechaResponse: String? = null
    @SerializedName("version")
    var version: String? = null

}
