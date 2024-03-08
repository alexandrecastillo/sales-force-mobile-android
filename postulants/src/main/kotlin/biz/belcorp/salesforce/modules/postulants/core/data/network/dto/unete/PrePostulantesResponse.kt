package biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete

import biz.belcorp.salesforce.core.data.network.dto.BaseResponse
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import com.google.gson.annotations.SerializedName

data class PrePostulantesResponse(val name: String = Constant.EMPTY_STRING) : BaseResponse() {

    @SerializedName("resultado")
    var resultado: PrePostulantesContenidoResponse? = null

}
