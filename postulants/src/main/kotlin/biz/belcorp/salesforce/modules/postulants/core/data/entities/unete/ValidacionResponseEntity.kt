package biz.belcorp.salesforce.modules.postulants.core.data.entities.unete

import com.google.gson.annotations.SerializedName

class ValidacionResponseEntity {

    @SerializedName("enumEstadoCrediticio")
    var enumEstadoCrediticio: Int? = null

    @SerializedName("requiereAprobacionSAC")
    var requiereAprobacionSAC: Boolean? = null

    @SerializedName("mensaje")
    var mensaje: String? = null

    @SerializedName("respuestaServicio")
    var respuestaServicio: String? = null

}
