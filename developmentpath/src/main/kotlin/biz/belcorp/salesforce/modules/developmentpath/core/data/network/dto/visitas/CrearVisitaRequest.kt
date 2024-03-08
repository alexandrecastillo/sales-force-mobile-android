package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.visitas

import com.google.gson.annotations.SerializedName

class CrearVisitaRequest {

    @SerializedName("planVisitaId")
    var planVisitaId: Long? = null

    @SerializedName("codigoConsultora")
    var codigoConsultora: String? = null

    @SerializedName("numeroDocumento")
    var numeroDocumento: String? = null

    @SerializedName("fechaPlanificacion")
    var fechaPlanificacion: String? = null

    @SerializedName("fechaReprogramacion")
    var fechaReprogramacion: String? = null

    @SerializedName("fechaVisita")
    var fechaVisita: String? = null

    @SerializedName("tipsId")
    var tipsId: Long? = null

    @SerializedName("rol")
    var rol: String? = null

    @SerializedName("consultoraId")
    var consultoraId: Long? = null

    @SerializedName("idLocal")
    var idLocal: Long? = null
}
