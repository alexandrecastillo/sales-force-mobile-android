package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.eventos

import biz.belcorp.salesforce.core.data.network.dto.BaseResponse
import biz.belcorp.salesforce.core.entities.sql.eventos.EventoRddEntity
import biz.belcorp.salesforce.core.entities.sql.eventos.EventoRddXUaEntity
import com.google.gson.annotations.SerializedName

data class EventosRddResponse(val resultado: Resultado) : BaseResponse() {

    class Resultado(
        @SerializedName("listEventoRDD")
        val eventos: List<EventoRddEntity>,

        @SerializedName("listEventoRDDPorUA")
        val eventosXUa: List<EventoRddXUaEntity>,

        @SerializedName("fechaServidor")
        val tiempo: Long
    )
}
