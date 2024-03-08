package biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete

import biz.belcorp.salesforce.modules.postulants.utils.Constant
import com.google.gson.annotations.SerializedName

class ResponseZonaContrasenia(val name: String = Constant.EMPTY_STRING) {
    @SerializedName("Estado")
    var estado: Int? = null
}
