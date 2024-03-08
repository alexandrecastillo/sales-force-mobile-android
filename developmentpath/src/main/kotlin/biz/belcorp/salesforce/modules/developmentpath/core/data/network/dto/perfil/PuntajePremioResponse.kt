package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.perfil

import biz.belcorp.salesforce.core.data.network.dto.BaseResponse
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.puntaje.PuntajePremio
import com.google.gson.annotations.SerializedName

class PuntajePremioResponse : BaseResponse() {

    @SerializedName("resultado")
    lateinit var resultado: PuntajePremio

    class PuntajePremio {

        @SerializedName("campania")
        val campania: String = ""

        @SerializedName("nivel")
        val nivel: Int = 0

        @SerializedName("puntosMeta")
        val puntosMeta: Int = 0

        @SerializedName("puntosObtenidos")
        val puntosObtenidos: Int = 0

        @SerializedName("premio")
        val premio: String = ""

        fun parse() = PuntajePremio(
            nivel = nivel,
            puntosMeta = puntosMeta,
            puntosObtenidos = puntosObtenidos,
            premio = premio
        )
    }
}
