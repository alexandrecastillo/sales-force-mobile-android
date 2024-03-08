package biz.belcorp.salesforce.core.data.repository.browser.cloud.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MyAcademyResponseDto {

    @SerialName("Code")
    var code: String? = null
    @SerialName("Message")
    var message: String? = null
    @SerialName("Data")
    var data: Data? = null

    @Serializable
    class Data {
        @SerialName("Token")
        var token: String? = null
        @SerialName("UrlMiAcademia")
        var urlMiAcademia: String? = null
    }

}
