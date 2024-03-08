package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.graficos

import com.google.gson.annotations.SerializedName

class ValoresProductividadData {
    @SerializedName("campania")
    var campania: String? = null
    @SerializedName("valor")
    var valor: String? = null
}
