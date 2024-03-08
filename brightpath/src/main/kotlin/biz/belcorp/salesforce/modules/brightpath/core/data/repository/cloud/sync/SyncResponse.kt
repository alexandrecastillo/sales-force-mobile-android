package biz.belcorp.salesforce.modules.brightpath.core.data.repository.cloud.sync

import biz.belcorp.salesforce.core.entities.sql.consultora.IndicadorCambioNivelEntity
import com.google.gson.annotations.SerializedName

class SyncResponse {

    @SerializedName("resultado")
    var result: Result? = null

    class Result {

        @SerializedName("data")
        var data: Data? = null

        class Data {

            @SerializedName("indicadorCambioNivel")
            var indicadorCambioNivel: List<IndicadorCambioNivelEntity> = emptyList()

        }

    }

}
