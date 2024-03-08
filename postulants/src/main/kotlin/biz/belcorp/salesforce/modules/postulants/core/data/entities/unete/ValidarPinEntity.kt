package biz.belcorp.salesforce.modules.postulants.core.data.entities.unete

import com.google.gson.annotations.SerializedName

class ValidarPinEntity {

    @SerializedName("pais")
    var pais: String? = null

    @SerializedName("solicitudPostulanteID")
    var solicitudPostulanteID: Int? = 0

    @SerializedName("numeroDocumento")
    var numeroDocumento: String? = null

    @SerializedName("PIN")
    var PIN: String? = null

}
