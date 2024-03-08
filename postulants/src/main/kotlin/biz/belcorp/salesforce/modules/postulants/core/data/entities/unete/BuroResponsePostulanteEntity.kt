package biz.belcorp.salesforce.modules.postulants.core.data.entities.unete

import com.google.gson.annotations.SerializedName

class BuroResponsePostulanteEntity {

    @SerializedName("esPostulante")
    var esPostulante: Boolean? = null

    @SerializedName("esConsultora")
    var esConsultora: Boolean? = null

    @SerializedName("esPotencial")
    var esPotencial: Boolean? = null

    @SerializedName("nombreCompleto")
    var nombreCompleto: String? = null

    @SerializedName("estadoActividad")
    var estadoActividad: Int? = null

}
