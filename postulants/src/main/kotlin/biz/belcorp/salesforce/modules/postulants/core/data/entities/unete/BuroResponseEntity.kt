package biz.belcorp.salesforce.modules.postulants.core.data.entities.unete

import com.google.gson.annotations.SerializedName

class BuroResponseEntity {

    @SerializedName("estadoCrediticio")
    var estadoCrediticio: Boolean? = null

    @SerializedName("enumEstadoCrediticioID")
    var enumEstadoCrediticioID: Int? = null

    @SerializedName("bloqueosInternos")
    var bloqueosInternos: Boolean? = null

    @SerializedName("mensajeError")
    var mensajeError: String? = null

    @SerializedName("tipoSolicitud")
    var tipoSolicitud: String? = null

    @SerializedName("buroResponse")
    var buroResponse: String? = null

    @SerializedName("requiereAprobacionSAC")
    var requiereAprobacionSAC: Boolean? = null

    @SerializedName("existePostulante")
    var existePostulante: BuroResponsePostulanteEntity? = null

}
