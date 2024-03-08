package biz.belcorp.salesforce.modules.consultants.core.data.repository.sync.dto

import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultorasPdVCierrePeriodoEntity
import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultorasPdVPosibleCambioNivelEntity
import com.google.gson.annotations.SerializedName


class SyncResponse {

    @SerializedName("resultado")
    var resultado: Resultado? = null

    class Resultado {

        companion object {

            const val PARCIAL = "parcial"
            const val FULL = "full"

        }

        @SerializedName("tipo")
        var tipo: String? = null

        @SerializedName("motivo")
        var motivo: String? = null

        @SerializedName("excepcion")
        var excepcion: String? = null

        @SerializedName("data")
        var data: Data? = null

        @SerializedName("fechaServidor")
        var fechaServidor: String? = ""

        class Data {

            @SerializedName("listaConsultorasPdVPosibleCambioNivel")
            var consultorasPdVPosibleCambioNivel: List<ConsultorasPdVPosibleCambioNivelEntity> =
                emptyList()

            @SerializedName("listaConsultorasNivelCierrePeriodo")
            var consultorasNivelCierrePeriodo: List<ConsultorasPdVCierrePeriodoEntity> =
                emptyList()

        }

        fun clone(): Resultado {
            val resultado = Resultado()
            resultado.tipo = tipo
            resultado.excepcion = excepcion
            resultado.motivo = motivo
            resultado.fechaServidor = fechaServidor
            resultado.data = null
            return resultado
        }

    }

}
