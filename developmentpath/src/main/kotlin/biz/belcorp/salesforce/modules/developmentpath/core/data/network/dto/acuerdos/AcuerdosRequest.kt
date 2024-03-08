package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.acuerdos

import com.google.gson.annotations.SerializedName

class AcuerdosRequest(
    @SerializedName("IndicadorAcuerdoRDD")
    val acuerdos: List<AcuerdoRequest>) {

    class AcuerdoRequest(
        @SerializedName("id")
        val acuerdoId: Long?,

        @SerializedName("region")
        val region: String?,

        @SerializedName("zona")
        val zona: String?,

        @SerializedName("seccion")
        val seccion: String?,

        @SerializedName("consultoraId")
        val consultoraId: Int?,

        @SerializedName("campania")
        val campania: String?,

        @SerializedName("contenido")
        val comentario: String?,

        @SerializedName("fechaRegistro")
        val fechaRegistro: String?,

        @SerializedName("cumplimiento")
        val cumplimiento: Int?)
}
