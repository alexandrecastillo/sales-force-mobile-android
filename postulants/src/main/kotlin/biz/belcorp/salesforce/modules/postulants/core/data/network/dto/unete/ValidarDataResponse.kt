package biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete

import com.google.gson.annotations.SerializedName

class ValidarDataResponse {

    @SerializedName("id")
    var identifier: String? = null
    @SerializedName("fechaRequest")
    var fechaRequest: String? = null
    @SerializedName("httpStatus")
    var httpStatus: Int? = null
    @SerializedName("resultado")
    var resultado: Boolean? = false
    @SerializedName("fechaResponse")
    var fechaResponse: String? = null
    @SerializedName("version")
    var version: String? = null
}
