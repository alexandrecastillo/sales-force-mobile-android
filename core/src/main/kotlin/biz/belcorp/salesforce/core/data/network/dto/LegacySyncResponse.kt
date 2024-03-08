package biz.belcorp.salesforce.core.data.network.dto

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

open class LegacySyncResponse<T> : BaseResponse() {

    companion object {
        const val NONE = "none"
        const val PARCIAL = "parcial"
        const val FULL = "full"
    }

    @SerializedName("resultado")
    var resultado: Resultado<T>? = null

    class Resultado<T> {

        @SerializedName("tipo")
        var tipo: String? = null

        @SerializedName("motivo")
        var motivo: String? = null

        @SerializedName("excepcion")
        var excepcion: String? = null

        @SerializedName("data")
        var data: T? = null

        @SerializedName("fechaServidor")
        var fechaServidor: String? = ""

        fun clone() = Resultado<T>().apply {
            this.tipo = this@Resultado.tipo
            this.excepcion = this@Resultado.excepcion
            this.motivo = this@Resultado.motivo
            this.fechaServidor = this@Resultado.fechaServidor
            this.data = null
        }

        fun print(tag: String) {
            val gson = GsonBuilder().setPrettyPrinting().create()
            val json = gson.toJson(clone())
            println("$tag $json")
        }

    }

}
