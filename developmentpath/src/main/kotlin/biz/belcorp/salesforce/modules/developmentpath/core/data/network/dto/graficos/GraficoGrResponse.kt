package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.graficos

import biz.belcorp.salesforce.core.data.network.dto.BaseResponse
import com.google.gson.annotations.SerializedName

class GraficoGrResponse : BaseResponse() {

    @SerializedName("resultado")
    var graficosGr: List<GraficoGrData>? = emptyList()
}
