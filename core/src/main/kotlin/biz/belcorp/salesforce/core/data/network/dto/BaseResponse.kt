package biz.belcorp.salesforce.core.data.network.dto

import com.google.gson.annotations.SerializedName

open class BaseResponse {

    @SerializedName("identifier")
    var identifier: String? = null

    @SerializedName("fechaRequest")
    var fechaRequest: String? = null

    @SerializedName("httpStatus")
    var httpStatus: Int? = null

    @SerializedName("fechaResponse")
    var fechaResponse: String? = null

    @SerializedName("version")
    var version: String? = null
}
