package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.graficos

import com.google.gson.annotations.SerializedName

class ProductividadData {

    @SerializedName("zona")
    var zona: String? = null
    @SerializedName("valores")
    var valores: List<ValoresProductividadData> = emptyList()
}
