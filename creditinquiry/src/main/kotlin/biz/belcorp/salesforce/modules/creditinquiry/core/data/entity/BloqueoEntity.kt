package biz.belcorp.salesforce.modules.creditinquiry.core.data.entity

import com.google.gson.annotations.SerializedName

class BloqueoEntity {

    @SerializedName("MotivoBloqueo")
    var motivoBloqueo: String? = null

    @SerializedName("Observacion")
    var observacion: String? = null

    @SerializedName("TipoBloqueo")
    var tipoBloqueo: String? = null

    @SerializedName("item")
    var item: Int? = null

}
