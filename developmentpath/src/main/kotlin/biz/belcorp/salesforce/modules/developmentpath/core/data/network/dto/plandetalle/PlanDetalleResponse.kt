package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.plandetalle

import biz.belcorp.salesforce.core.data.network.dto.BaseResponse
import biz.belcorp.salesforce.core.entities.sql.eventos.EventoRddEntity
import biz.belcorp.salesforce.core.entities.sql.eventos.EventoRddXUaEntity
import biz.belcorp.salesforce.core.entities.sql.path.VisitaXFechaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.plan.FechaNoHabilFacturacionEntity
import com.google.gson.annotations.SerializedName

class PlanDetalleResponse : BaseResponse() {

    @SerializedName("resultado")
    var response: Response? = null

    class Response {

        @SerializedName("indicadorTotalRutaDesarrolloDetallesList")
        var detallesPlanRutaRDD: List<DetallePlanRutaRDDEntity>? = null

        @SerializedName("listaVisitaXFechaRDD")
        var visitasPorFecha: List<VisitaXFechaRDDEntity>? = null

        @SerializedName("fechaNoHabilFacturacion")
        var fechaNoHabilFacturacion: List<FechaNoHabilFacturacionEntity> = emptyList()

        @SerializedName("listEventoRDD")
        var eventosRdd: List<EventoRddEntity> = emptyList()

        @SerializedName("listEventoRDDPorUA")
        var eventosXUaRdd: List<EventoRddXUaEntity> = emptyList()

    }
}
