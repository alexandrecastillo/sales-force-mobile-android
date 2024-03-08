package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.visitas

import com.google.gson.annotations.SerializedName

class CrearVisitaResponse {

    @SerializedName("resultado")
    var resultado: List<ParLocalServer> = emptyList()

    class ParLocalServer {
        @SerializedName("id")
        var idServer: Long? = null

        @SerializedName("idLocal")
        var idLocal: Long? = null
    }
}
