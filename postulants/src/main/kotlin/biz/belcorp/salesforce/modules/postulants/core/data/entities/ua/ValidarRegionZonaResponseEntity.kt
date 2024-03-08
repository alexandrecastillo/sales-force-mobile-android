package biz.belcorp.salesforce.modules.postulants.core.data.entities.ua

import com.google.gson.annotations.SerializedName

class ValidarRegionZonaResponseEntity {

    @SerializedName("identifier")
    var identifier: String? = null
    @SerializedName("fechaResponse")
    var fechaRequest: String? = null
    @SerializedName("httpStatus")
    var httpStatus: String? = null
    @SerializedName("version")
    var version: String? = null
    @SerializedName("resultado")
    var resultado: ValidarRegionZonaEntity? = null

}
