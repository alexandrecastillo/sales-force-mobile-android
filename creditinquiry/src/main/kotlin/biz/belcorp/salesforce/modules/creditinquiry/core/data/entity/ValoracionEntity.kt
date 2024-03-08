package biz.belcorp.salesforce.modules.creditinquiry.core.data.entity

import com.google.gson.annotations.SerializedName

class ValoracionEntity {

    @SerializedName("Tipo")
    var tipo: String? = null

    @SerializedName("Valor")
    var valor: String? = null

}
