package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.focos

import com.google.gson.annotations.SerializedName

data class DetalleFocoApiResponse(
    val identifier: String?,
    val fechaRequest: String?,
    val httpStatus: Int,
    val resultado: Resultado?,
    val fechaResponse: String?,
    val version: String?
) {

    data class Resultado(
        val error: String?,
        @SerializedName("error_description")
        val errorDescription: String?,
        @SerializedName("error_stacktrace")
        val errorStacktrace: String?
    )
}
