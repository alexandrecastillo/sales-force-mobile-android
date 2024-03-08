package biz.belcorp.salesforce.core.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ErrorBody(
    @SerialName("httpStatus")
    val httpStatus: Int,
    @SerialName("resultado")
    val result: Result
) {

    @Serializable
    class Result(
        @SerialName("message")
        val message: String,
        @SerialName("exceptionMessage")
        val exceptionMessage: String
    )

}
