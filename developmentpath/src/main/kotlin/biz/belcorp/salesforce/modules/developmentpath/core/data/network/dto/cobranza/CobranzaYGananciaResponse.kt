package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.cobranza

import biz.belcorp.salesforce.core.data.network.dto.BaseResponse
import biz.belcorp.salesforce.core.entities.sql.datos.CobranzaYGananciaEntity
import com.google.gson.annotations.SerializedName

class CobranzaYGananciaResponse : BaseResponse() {

    @SerializedName("resultado")
    var resultado: List<CobranzaYGananciaEntity>? = null

}
