package biz.belcorp.salesforce.modules.postulants.core.data.entities.crediticio

import com.google.gson.annotations.SerializedName

class ValoracionEntity {

    @SerializedName("Tipo")
    var tipo: String? = null

    @SerializedName("Valor")
    var valor: String? = null

}
