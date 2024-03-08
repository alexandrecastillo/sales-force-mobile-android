package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.concursos

import biz.belcorp.salesforce.core.data.network.dto.BaseResponse
import com.google.gson.annotations.SerializedName

class ConcursosResponse : BaseResponse() {
    @SerializedName("resultado")
    var resultado: String = "[]"
}
