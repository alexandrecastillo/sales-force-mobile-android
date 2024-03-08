package biz.belcorp.salesforce.modules.creditinquiry.core.data.entity

import com.google.gson.annotations.SerializedName

class DeudaExternaEntity {

    @SerializedName("entidadCrediticia")
    var entidadCrediticia: String? = null

    @SerializedName("monto")
    var monto: String? = null

    @SerializedName("simboloMoneda")
    var simboloMoneda: String? = null

}
