package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.sync

import com.google.gson.annotations.SerializedName

class DatosConsultoraRequest {

    @SerializedName("codigo")
    var codigo: String = ""

    @SerializedName("telefono")
    var telefono: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("tipoTelefonoDefault")
    var tipoTelefono: Int? = null
}
