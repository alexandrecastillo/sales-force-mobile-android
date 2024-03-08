package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.ruta

import com.google.gson.annotations.SerializedName

data class VisitasPorFechaRequest(
    @SerializedName("VisitaXFechaRDD") val visitaXFechaRDD: List<VisitaXFechaRDD>
) {
    data class VisitaXFechaRDD(
        @SerializedName("Id") val id: Long?,
        @SerializedName("FechaVisita") val fechaVisita: String,
        @SerializedName("CantidadVisita") val cantidadVisita: Int
    )
}
