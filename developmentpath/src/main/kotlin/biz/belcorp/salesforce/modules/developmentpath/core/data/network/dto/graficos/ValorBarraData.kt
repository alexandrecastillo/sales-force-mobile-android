package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.graficos

import com.google.gson.annotations.SerializedName

class ValorBarraData {

    @SerializedName("campania")
    var campania: String? = null
    @SerializedName("valor")
    var valor: Double? = null
    @SerializedName("porcentaje")
    var porcentaje: Int? = null
}
