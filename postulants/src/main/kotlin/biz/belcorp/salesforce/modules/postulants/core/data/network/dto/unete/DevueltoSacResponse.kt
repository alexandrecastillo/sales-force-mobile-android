package biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete

import biz.belcorp.salesforce.modules.postulants.core.data.entities.unete.DevueltoSacEntity
import com.google.gson.annotations.SerializedName

class DevueltoSacResponse {
    @SerializedName("id")
    var identifier: String? = null
    @SerializedName("fechaRequest")
    var fechaRequest: String? = null
    @SerializedName("httpStatus")
    var httpStatus: Int? = null
    @SerializedName("resultado")
    var resultado: DevueltoSacEntity? = null
    @SerializedName("fechaResponse")
    var fechaResponse: String? = null
    @SerializedName("version")
    var version: String? = null
}
