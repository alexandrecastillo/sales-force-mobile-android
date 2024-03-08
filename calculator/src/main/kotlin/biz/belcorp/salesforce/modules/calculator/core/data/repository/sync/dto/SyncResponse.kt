package biz.belcorp.salesforce.modules.calculator.core.data.repository.sync.dto

import biz.belcorp.salesforce.core.data.network.dto.BaseResponse
import biz.belcorp.salesforce.core.entities.calculator.*
import biz.belcorp.salesforce.core.entities.sql.calculator.CalculadoraMontoFijoEntity
import biz.belcorp.salesforce.core.entities.sql.calculator.CalculatingResultEntity
import biz.belcorp.salesforce.core.entities.sql.calculator.SocialBonusEntity
import com.google.gson.annotations.SerializedName

class SyncResponse : BaseResponse() {

    @SerializedName("resultado")
    var resultado: Resultado? = null

    class Resultado {

        companion object {

            const val NONE = "none"
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

            @SerializedName("listaNivelSuperiorSE")
            var nivelSuperiorSE: List<UpperLevelEntity> = emptyList()

            @SerializedName("listaParametroNivel")
            var parametroNivel: List<LevelParameterEntity> = emptyList()

            @SerializedName("variableSocia")
            var variableSocia: PartnerVariableEntity? = null

            @SerializedName("listaBonoSocia")
            var bonoSocia: List<SocialBonusEntity> = emptyList()

            @SerializedName("listaDetallePagoTabla")
            var detallePagoTabla: List<PaymentDetailEntity> = emptyList()

            @SerializedName("listaDetalleConcursoSE")
            var detalleConcursoSE: List<ContestDetailEntity> = emptyList()

            @SerializedName("resultadoCalculadora")
            var resultadoCalculadora: CalculatingResultEntity? = null

            @SerializedName("listaVariablesMontoFijo")
            var variablesMontoFijo: List<CalculadoraMontoFijoEntity> = emptyList()
        }

        fun clone(): Resultado {
            val resultado =
                Resultado()
            resultado.tipo = tipo
            resultado.excepcion = excepcion
            resultado.motivo = motivo
            resultado.fechaServidor = fechaServidor
            resultado.data = null
            return resultado
        }

    }

}
