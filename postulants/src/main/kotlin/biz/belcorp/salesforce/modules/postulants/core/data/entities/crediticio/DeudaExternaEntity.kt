package biz.belcorp.salesforce.modules.postulants.core.data.entities.crediticio

import com.google.gson.annotations.SerializedName

class DeudaExternaEntity {

    @SerializedName("entidadCrediticia")
    var entidadCrediticia: String? = null

    @SerializedName("monto")
    var monto: String? = null

    @SerializedName("simboloMoneda")
    var simboloMoneda: String? = null

}
