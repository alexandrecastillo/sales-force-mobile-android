package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.base

import com.google.gson.annotations.SerializedName

data class BaseResponseGeneric<out T> constructor(
    @SerializedName("identifier")
    val identifier: String? = null,
    @SerializedName("fechaRequest")
    val fechaRequest: String? = null,
    @SerializedName("httpStatus")
    val httpStatus: Int? = null,
    @SerializedName("fechaResponse")
    val fechaResponse: String? = null,
    @SerializedName("version")
    val version: String? = null,
    @SerializedName("resultado")
    val resultado: T? = null
)
