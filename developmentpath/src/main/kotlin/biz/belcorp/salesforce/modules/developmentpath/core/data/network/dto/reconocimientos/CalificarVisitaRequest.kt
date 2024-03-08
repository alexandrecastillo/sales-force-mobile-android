package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.reconocimientos

import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import com.google.gson.annotations.SerializedName

class CalificarVisitaRequest(
    @SerializedName("IdReconocimiento")
    var idReconocimiento: Long = Constant.CERO.toLong(),

    @SerializedName("Calificacion")
    var calificacion: Int = Constant.UNO,

    @SerializedName("Comentarios")
    var comentarios: String? = null)
