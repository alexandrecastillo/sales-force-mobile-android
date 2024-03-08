package biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete

import com.google.gson.annotations.SerializedName

class EnvioSMSResponse{
    @SerializedName("id")
    var identifier: String? = null
    @SerializedName("fechaRequest")
    var fechaRequest: String? = null
    @SerializedName("httpStatus")
    var httpStatus: Int? = null
    @SerializedName("resultado")
    var resultado: String? = null
    @SerializedName("fechaResponse")
    var fechaResponse: String? = null
    @SerializedName("version")
    var version: String? = null
}
