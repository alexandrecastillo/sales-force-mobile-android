package biz.belcorp.salesforce.modules.postulants.core.data.entities.crediticio

import com.google.gson.annotations.SerializedName


class ConsultarDCResultEntity {

    @SerializedName("Estado")
    var estado: String? = null

    @SerializedName("NomEstado")
    var nomEstado: String? = null

    @SerializedName("Nombre")
    var nombre: String? = null

    @SerializedName("NumDocumento")
    var numDocumento: String? = null

    @SerializedName("Resultado")
    var resultado: String? = null

}
