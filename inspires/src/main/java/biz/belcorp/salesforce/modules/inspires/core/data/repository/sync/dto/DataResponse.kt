package biz.belcorp.salesforce.modules.inspires.core.data.repository.sync.dto

import biz.belcorp.salesforce.core.entities.sql.inspires.*
import com.google.gson.annotations.SerializedName

class DataResponse {

    @SerializedName("indicadorInspira")
    var indicadorInspira: InspiraIndicadorEntity? = null

    @SerializedName("listInspiraRanking")
    var rankingInspira: List<InspiraRankingEntity> = emptyList()

    @SerializedName("listInspiraAvances")
    var avancesInspira: List<InspiraAvancesEntity> = emptyList()

    @SerializedName("listInspiraCondiciones")
    var condicionesInspira: List<InspiraCondicionesEntity> = emptyList()

    @SerializedName("listInspiraCondicionesLeyenda")
    var condicionesLeyendaInspira: List<InspiraCondicionesLeyendaEntity> = emptyList()

    @SerializedName("listInspiraCondicionesLeyendaDetalle")
    var condicionesLeyendaDetalleInspira: List<InspiraCondicionesLeyendaDetalleEntity> = emptyList()

    @SerializedName("listInspiraAvancesPeriodo")
    var avancesPeriodoInspira: List<InspiraAvancesPeriodoEntity> = emptyList()
}
