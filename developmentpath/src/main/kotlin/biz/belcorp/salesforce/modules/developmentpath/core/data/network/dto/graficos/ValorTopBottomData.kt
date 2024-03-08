package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.graficos

import com.google.gson.annotations.SerializedName

class ValorTopBottomData {

    @SerializedName("zona")
    var zona: String? = null
    @SerializedName("tipo")
    var tipo: String? = null
    @SerializedName("valor")
    var valor: Double? = null
    @SerializedName("porcentaje")
    var porcentaje: Int? = null
}
