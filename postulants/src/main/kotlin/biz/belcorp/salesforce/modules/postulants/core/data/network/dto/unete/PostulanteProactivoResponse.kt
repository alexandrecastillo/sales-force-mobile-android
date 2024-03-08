package biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete

import com.google.gson.annotations.SerializedName

class PostulanteProactivoResponse {

    @SerializedName("error")
    var error: String? = null

    @SerializedName("error_description")
    var errorDescription: String? = null

    @SerializedName("error_stacktrace")
    var errorStackTrace: String? = null
    
}
